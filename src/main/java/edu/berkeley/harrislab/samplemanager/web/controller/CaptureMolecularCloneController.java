package edu.berkeley.harrislab.samplemanager.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.berkeley.harrislab.samplemanager.domain.*;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.*;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Everts Morales Reyes.
 */

@Controller
@RequestMapping("/capture/molecularClone/*")
public class CaptureMolecularCloneController {
    private static final Logger logger = LoggerFactory.getLogger(CaptureMolecularCloneController.class);

    @Resource(name="PlasmidsService")
    private PlasmidsService PlasmidsService;

    @Resource(name="boxService")
    private BoxService boxService;
    @Resource(name="rackService")
    private RackService rackService;
    @Resource(name="equipService")
    private EquipService equipService;

    @Resource(name="auditTrailService")
    private AuditTrailService auditTrailService;
    @Resource(name="messageResourceService")
    private MessageResourceService messageResourceService;


    @Resource(name="usuarioService")
    private UsuarioService usuarioService;

    @Resource(name="visitsService")
    private VisitsService visitsService;

    @Resource(name="PlasmidsFilterService")
    private PlasmidsFilterService  PlasmidsFilterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException {
        logger.debug("Mostrando registros en JSP");


        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureplasmidspage");
        return "capture/molecularClone/list";
    }


    /**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
    public String initAddEntityForm(Model model) {
        List<Rack> racks = this.rackService.getActiveRacks();
        model.addAttribute("racks",racks);
        List<Box> boxes = boxService.getBoxes();
        model.addAttribute("boxes", boxes);
        List<Equip> equips = equipService.getEquips();
        model.addAttribute("equips", equips);
        List<MessageResource> sinos = this.messageResourceService.getCatalogo("CAT_SINO");
        model.addAttribute("sinos",sinos);

        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureplasmidspage");
        return "capture/molecularClone/enterNewForm";
    }

    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
    public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
        Plasmids entidadEditar = this.PlasmidsService.getPlasmidsBySystemId(systemId) ;
        if(entidadEditar!=null){
            List<MessageResource> types = this.messageResourceService.getCatalogo("CAT_SP_TYPE");
            model.addAttribute("types",types);
            List<MessageResource> sinos = this.messageResourceService.getCatalogo("CAT_SINO");
            model.addAttribute("sinos",sinos);
            List<MessageResource> volUnits = this.messageResourceService.getCatalogo("CAT_VOL_UNITS");
            model.addAttribute("volUnits",volUnits);
            List<MessageResource> conditions = this.messageResourceService.getCatalogo("CAT_SP_COND");
            model.addAttribute("conditions",conditions);

            List<Equip> equips = this.equipService.getActiveEquips();
            model.addAttribute("equips",equips);

            List<Rack> racks = this.rackService.getRacks(entidadEditar.getLoc_freezer().toString());
            model.addAttribute("racks",racks);

            List<Box> boxes = this.boxService.getActiveBoxes(entidadEditar.getLoc_rack().toString());
            model.addAttribute("boxes",boxes);

            List<String> posiciones = new ArrayList<String>();
            Box box = this.boxService.getBoxBySystemId(entidadEditar.getLoc_box().toString());
            if (box!=null){
                for(Integer i = 1; i<=box.getCapacity(); i++){
                    posiciones.add(i.toString());
                }
            }
            List <Plasmids> specBox = this.PlasmidsService.getPlasmidsForBox(entidadEditar.getLoc_box().toString()) ;
            for(Plasmids spec:specBox) {
                String posicion = String.valueOf(spec.getLoc_pos());
                posiciones.remove(posicion);
            }

            model.addAttribute("posiciones",posiciones);

            model.addAttribute("entidad",entidadEditar);
            List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
            model.addAttribute("substudies",substudies);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditrecombinantproteinpage");
            return "capture/Plasmids/editForm";
        }
        else{
            return "403";
        }
    }


    /**
     * Custom handler for saving.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEditEntity/", method=RequestMethod.POST)
    public ResponseEntity<String> processEditEntity(@RequestParam( value="systemId", required=true ) String systemId
            , @RequestParam( value="plasmids_id", required=true) String plasmids_id
            , @RequestParam( value="plasmid_name", required=true) String plasmid_name
            , @RequestParam( value="sequencing_confirmed", required=false, defaultValue="") String sequencing_confirmed
            , @RequestParam( value="sequencing_primers", required=true) String sequencing_primers
            , @RequestParam( value="backbone_vector", required=false, defaultValue="") String backbone_vector
            , @RequestParam( value="antibiotic_resistant", required=false, defaultValue="") String antibiotic_resistant
            , @RequestParam( value="growth_conditions", required=false, defaultValue="") String growth_conditions
            , @RequestParam( value="midiprep_purified", required=false, defaultValue="") String midiprep_purified
            , @RequestParam( value="concentration", required=false, defaultValue="") String concentration
            , @RequestParam( value="comments", required=false, defaultValue="") String comments
            , @RequestParam( value="glycerol_stock", required=true) String glycerol_stock
            , @RequestParam( value="loc_freezer", required=false, defaultValue="") String loc_freezer
            , @RequestParam( value="loc_rack", required=false, defaultValue="") String loc_rack
            , @RequestParam( value="loc_box", required=false) String loc_box
            , @RequestParam( value="loc_pos", required=false, defaultValue="") Integer loc_pos

    )
    {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso =  null;
            Date fechaAlmacenamiento =  null;
            WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

            Plasmids  entidad  = new Plasmids(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!systemId.equals("")) entidad.setSystemId(systemId);

            if (!plasmids_id.equals("")) entidad.setPlasmids_id(plasmids_id);
            if (!plasmid_name.equals("")) entidad.setPlasmid_name(plasmid_name);
            if (!sequencing_confirmed.equals("")) entidad.setSequencing_confirmed(sequencing_confirmed);


            if (!sequencing_primers.equals("")) entidad.setSequencing_primers(sequencing_primers); ;
            if (!backbone_vector.equals("")) entidad.setBackbone_vector(backbone_vector); ;
            if (!antibiotic_resistant.equals("")) entidad.setAntibiotic_resistant((antibiotic_resistant)); ;
            if (!growth_conditions.equals("")) entidad.setGrowth_conditions(growth_conditions); ;

            if (!midiprep_purified.equals("")) entidad.setMidiprep_purified(midiprep_purified);

            if (!concentration.equals("")) entidad.setConcentration(concentration);

            if (!comments.equals("")) entidad.setComments(comments);

            if (!glycerol_stock.equals("")) entidad.setGlycerol_stock(glycerol_stock);

            if (!loc_freezer.equals("")) entidad.setLoc_freezer(loc_freezer);

            if (!loc_rack.equals("")) entidad.setLoc_rack(loc_rack);

            if (!loc_box.equals("")) entidad.setLoc_box(loc_box); ;

            if (!loc_pos.equals(1) ) {
                if (!loc_pos.equals("")) entidad.setLoc_pos(loc_pos) ;
            }


            this.PlasmidsService.savePlasmids(entidad);

            return createJsonResponse(entidad);
        }
        catch (DataIntegrityViolationException e){
            String message = e.getMostSpecificCause().getMessage();
            Gson gson = new Gson();
            String json = gson.toJson(message);
            return createJsonResponse(json);
        }
        catch(Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return createJsonResponse(json);
        }

    }
    /**
     * M�todo para convertir estructura Json que se recibe desde el cliente a FiltroMx para realizar b�squeda de Mx(Vigilancia) y Recepci�n Mx(Laboratorio)
     *
     * @param strJson String con la informaci�n de los filtros
     * @return FiltroMx
     * @throws Exception
     */
    private PlasmidsFilters jsonToFilter(String strJson) throws Exception {
        JsonObject jObjectFiltro = new Gson().fromJson(strJson, JsonObject.class);
        PlasmidsFilters antFilters = new PlasmidsFilters();
        String plasmids_id = null;
        String plasmid_name = null;
        String sequencing_primers = null;


        Integer activeSearch = null;

        if (jObjectFiltro.get("plasmids_id") != null && !jObjectFiltro.get("plasmids_id").getAsString().isEmpty())
            plasmids_id = jObjectFiltro.get("plasmids_id").getAsString();
        if (jObjectFiltro.get("plasmid_name") != null && !jObjectFiltro.get("plasmid_name").getAsString().isEmpty())
            plasmid_name = jObjectFiltro.get("plasmid_name").getAsString();
        if (jObjectFiltro.get("sequencing_primers") != null && !jObjectFiltro.get("sequencing_primers").getAsString().isEmpty())
            sequencing_primers = jObjectFiltro.get("sequencing_primers").getAsString();


        if (jObjectFiltro.get("activeSearch") != null && !jObjectFiltro.get("activeSearch").getAsString().isEmpty())
            activeSearch = jObjectFiltro.get("activeSearch").getAsInt();


        antFilters.setPlasmids_id(plasmids_id);
        antFilters.setPlasmid_name(plasmid_name);
        antFilters.setSequencing_primers(sequencing_primers);

        antFilters.setActiveSearch(activeSearch);

        return antFilters;
    }

    /**
     * Custom handler for searching.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/search/", method=RequestMethod.GET)
    public @ResponseBody List<PlasmidsResults> searchProcess(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
        PlasmidsFilters specFilters = jsonToFilter(filtro);
        List<PlasmidsResults> plasmidsList;
        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");



        if(specFilters.getActiveSearch() == 0){
            plasmidsList = new ArrayList<>();
        }else {

            plasmidsList = PlasmidsFilterService.getPlasmidsByFilter(specFilters) ;
        }
        String lang = LocaleContextHolder.getLocale().getLanguage();
        for(PlasmidsResults antib:plasmidsList) {
            String descCatalogo = null;

        }

        System.out.println("plasmidsList = "+ plasmidsList.toArray().toString());
        return plasmidsList ;
    }

    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
    public ResponseEntity<String> processEntity(@RequestParam( value="plasmids_id", required=true ) String plasmids_id

            , @RequestParam( value="plasmid_name", required=true) String plasmid_name
            , @RequestParam( value="sequencing_confirmed", required=false, defaultValue="") String sequencing_confirmed
            , @RequestParam( value="sequencing_primers", required=true) String sequencing_primers
            , @RequestParam( value="backbone_vector", required=false, defaultValue="") String backbone_vector
            , @RequestParam( value="antibiotic_resistant", required=false, defaultValue="") String antibiotic_resistant
            , @RequestParam( value="growth_conditions", required=false, defaultValue="") String growth_conditions
            , @RequestParam( value="midiprep_purified", required=false, defaultValue="") String midiprep_purified
            , @RequestParam( value="concentration", required=false, defaultValue="") String concentration
            , @RequestParam( value="comments", required=false, defaultValue="") String comments
            , @RequestParam( value="glycerol_stock", required=true) String glycerol_stock
            , @RequestParam( value="equip", required=false, defaultValue="") String equip
            , @RequestParam( value="rack", required=false, defaultValue="") String rack
            , @RequestParam( value="boxSpecId", required=false) String boxSpecId
            , @RequestParam( value="position", required=false, defaultValue="") Integer position

    )
    {
        try{
            System.out.println("Entra al controlador CapturePlasmidsController");
            System.out.println("plasmids_id = "+ plasmids_id);
            System.out.println("plasmid_name = " + plasmid_name);
            System.out.println("sequencing_confirmed = " + sequencing_confirmed);
            System.out.println("sequencing_primers = "+ sequencing_primers);
            System.out.println("backbone_vector = "+ backbone_vector);
            System.out.println("antibiotic_resistant = "+ antibiotic_resistant);
            System.out.println("growth_conditions = "+ growth_conditions);
            System.out.println("midiprep_purified = "+ midiprep_purified);
            System.out.println(" concentration = "+ concentration);
            System.out.println("comments = "+ comments);
            System.out.println("glycerol_stock = "+ glycerol_stock);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso =  null;
            Date fechaAlmacenamiento =  null;
            WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
           Plasmids  entidad  = new Plasmids(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
            SpecimenStorage entidad2 = null;
            //RecombinantProtein entidad = this.RecombinantproteinService.getRecombinantProteinByRecombinantProtein_id(protrecombinante_id) ;
          //  entidad  = new RecombinantProtein(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');



            if (!plasmids_id.equals("")) entidad.setPlasmids_id(plasmids_id);
            if (!plasmid_name.equals("")) entidad.setPlasmid_name(plasmid_name);
            if (!sequencing_confirmed.equals("")) entidad.setSequencing_confirmed(sequencing_confirmed);
            if (!sequencing_primers.equals("")) entidad.setSequencing_primers(sequencing_primers);


            if (!backbone_vector.equals("")) entidad.setBackbone_vector(backbone_vector);
            if (!antibiotic_resistant.equals("")) entidad.setAntibiotic_resistant(antibiotic_resistant) ;
            if (!growth_conditions.equals("")) entidad.setGrowth_conditions(growth_conditions) ;
            if (!midiprep_purified.equals("")) entidad.setMidiprep_purified(midiprep_purified);

            if (!concentration.equals("")) entidad.setConcentration(concentration);
            if (!comments.equals("")) entidad.setComments(comments);
            if (!glycerol_stock.equals("")) entidad.setGlycerol_stock(glycerol_stock);

            if (!equip.equals("")) entidad.setLoc_freezer(equip);

            if (!rack.equals("")) entidad.setLoc_rack(rack);

            if (!boxSpecId.equals("")) entidad.setLoc_box(boxSpecId); ;

            if (!position.equals("") ) {
                if (!position.equals("")) entidad.setLoc_pos(position) ;
            }


            this.PlasmidsService.savePlasmids(entidad);

            return createJsonResponse(entidad);
        }
        catch (DataIntegrityViolationException e){
            String message = e.getMostSpecificCause().getMessage();
            Gson gson = new Gson();
            String json = gson.toJson(message);
            return createJsonResponse(json);
        }
        catch(Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
           return createJsonResponse(json);
        }

    }
    private ResponseEntity<String> createJsonResponse( Object o )
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
    }

    @RequestMapping(value = "/uploadEntity/", method = RequestMethod.GET )
    public String initUploadForm(Model model) {
        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureuploadantibodiepage");
        return "capture/plasmids/uploadExcel";
    }
    /**carga el archivo excel**/

    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
    public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        boolean checkLabReceiptDate = false;

         String plasmids_id;          //REQUIRED
         String plasmid_name;        //REQUIRED
         String sequencing_confirmed;
         String sequencing_primers;
         String backbone_vector;
         String antibiotic_resistant;  //REQUIRED
         String growth_conditions;
         String midiprep_purified;   //Yes/No
         String concentration;
         String comments;
         String glycerol_stock; //Yes/No      //REQUIRED
         String loc_freezer;               //REQUIRED
         String loc_rack;                 //REQUIRED
         String loc_box;                  //REQUIRED
         Integer loc_pos;                //REQUIRED


         String recordUser;
         String recordDate;
         String recordIp;

        int nuevos =0, viejos=0, id_no_validos=0;


        Plasmids entidad = new Plasmids();
        Plasmids entidad_nuevos = new Plasmids();
        List<Plasmids> entidades = new ArrayList<Plasmids>();
        List<Plasmids> entidades_nuevos = new ArrayList<Plasmids>();
        WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

        try {
            //Read the file
            Reader in = new InputStreamReader(file.getInputStream());
            InputStream in1 = file.getInputStream();
            //Define the format
            String fileLocation;
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
            FileOutputStream f = new FileOutputStream(fileLocation);
            int ch = 0;
            while ((ch = in1.read()) != -1) {
                f.write(ch);
            }
            f.flush();
            f.close();


            FileInputStream file2 = new FileInputStream(new File(fileLocation));

            //Crear Workbook para instanciar referencia a .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file2);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);


            int numFilas = sheet.getLastRowNum();
            modelMap.addAttribute("numFilas", numFilas-1);
            int numcol = 0;

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int contc = 2;
            int contfinalrows = 1;
            int camponoexiste = 0;
            int centinelafin = 0;
            while (rowIterator.hasNext()  ) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Cell c1 = row.getCell(contc);
                if(c1 != null && entidad == null)
                {
                    contc = contc + 1 ;
                }
                else {
                    contc = 2;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                int contf = 1;
                numcol = row.getLastCellNum();
                while (cellIterator.hasNext() && numcol > centinelafin ) {
                    int campoexiste = 0;
                    Cell c = row.getCell(contf);
                    Cell cell = cellIterator.next();

                    if ((contf != contfinalrows) && (c != null )){
                        /**  if (contc > 3)  {
                         contc = 2;
                         }**/
                        contfinalrows = contfinalrows - 1;
                    }

                    if  ((c != null ) )  {

                        /**Identificamos la tabla y el id
                         cada columna es un campo mas a actualizar
                         en sheet el getrow 0 son los encabezados
                         el encabezado de cada columna es el nombre de cada campo a partir de la columna 1**/


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("plasmids_id")){
                            plasmids_id = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            entidad = this.PlasmidsService.getPlasmidsByPlasmids_id(plasmids_id);
                            //   entidad_error = this.antibodyservice.getAntibodyByantibody_id(antibody_id);
                            if (entidad != null  ) {
                                entidad.setPlasmids_id(plasmids_id);
                                contfinalrows = contfinalrows + 1;
                                viejos = viejos + 1;
                            }else
                            {
                                if(!plasmids_id.toString().isEmpty()) {
                                    entidad.setPlasmids_id(plasmids_id);
                                    // entidades_nuevos.setAntibody_id(antibody_id);
                                    nuevos = nuevos + 1;
                                }
                                else {
                                    id_no_validos =  id_no_validos + 1 ;
                                }

                            }

                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("plasmid_name")){
                            plasmid_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setPlasmid_name(plasmid_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!plasmid_name.toString().isEmpty()) {
                                    entidad.setPlasmid_name(plasmid_name);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("sequencing_confirmed")){
                            sequencing_confirmed = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setSequencing_confirmed(sequencing_confirmed);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!sequencing_confirmed.toString().isEmpty()) {
                                    entidad.setSequencing_confirmed(sequencing_confirmed);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("sequencing_primers")){
                            sequencing_primers = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setSequencing_primers(sequencing_primers);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!sequencing_primers.toString().isEmpty()) {
                                    entidad.setSequencing_primers(sequencing_primers);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("backbone_vector")){
                            backbone_vector = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setBackbone_vector(backbone_vector);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!backbone_vector.toString().isEmpty()) {
                                    entidad.setBackbone_vector(backbone_vector);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("antibiotic_resistant")){
                            antibiotic_resistant = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setAntibiotic_resistant(antibiotic_resistant);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!antibiotic_resistant.toString().isEmpty()) {
                                    entidad.setAntibiotic_resistant(antibiotic_resistant);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("growth_conditions")){
                            growth_conditions = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setGrowth_conditions(growth_conditions);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!growth_conditions.toString().isEmpty()) {
                                    entidad.setGrowth_conditions(growth_conditions);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("midiprep_purified")){
                            midiprep_purified = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setMidiprep_purified(midiprep_purified);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!midiprep_purified.toString().isEmpty()) {
                                    entidad.setMidiprep_purified(midiprep_purified);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("concentration")){
                            concentration = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setConcentration(concentration);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!concentration.toString().isEmpty()) {
                                    entidad.setConcentration(concentration);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("comments")){
                            comments = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();

                            if (entidad != null  ) {
                                entidad.setComments(comments);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!comments.toString().isEmpty()) {
                                    entidad.setComments(comments);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("glycerol_stock")){
                            glycerol_stock = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setGlycerol_stock(glycerol_stock);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!glycerol_stock.toString().isEmpty()) {
                                    entidad.setGlycerol_stock(glycerol_stock);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("loc_freezer")){
                            loc_freezer = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            loc_freezer = equipService.getEquipByUserId(loc_freezer).getSystemId();
                            if (entidad != null  ) {
                                entidad.setLoc_freezer(loc_freezer);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!loc_freezer.toString().isEmpty()) {
                                    entidad.setLoc_freezer(loc_freezer);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("loc_rack")){
                            loc_rack = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            loc_rack = rackService.getRackByUserId(loc_rack).getSystemId();
                            if (entidad != null  ) {
                                entidad.setLoc_rack(loc_rack);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!loc_rack.toString().isEmpty()) {
                                    entidad.setLoc_rack(loc_rack);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("loc_box")){
                            loc_box = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            loc_box = boxService.getBoxByUserId(loc_box).getSystemId();
                            if (entidad != null  ) {
                                entidad.setLoc_box(loc_box);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!loc_box.toString().isEmpty()) {
                                    entidad.setLoc_box(loc_box);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("loc_pos")){
                            loc_pos = Integer.parseInt(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());

                            if (entidad != null  ) {
                                entidad.setLoc_pos((loc_pos));
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!loc_pos.toString().isEmpty()) {
                                    entidad.setLoc_pos((loc_pos));
                                }
                            }
                        }




                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("recordUser")){
                            recordUser = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();

                            if (entidad != null  ) {
                                entidad.setRecordUser(recordUser);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!recordUser.toString().isEmpty()) {
                                    entidad.setRecordUser(recordUser);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("recordDate")){
                            recordDate = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                            Date fecha2 = formato.parse(recordDate);
                            if (entidad != null  ) {
                                entidad.setRecordDate(fecha2);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!recordDate.toString().isEmpty()) {
                                    entidad.setRecordDate(fecha2);
                                }
                            }
                        }


                    } // fin C

                    if (c == null ) {
                        cell.setBlank();
                        contfinalrows = contf + contfinalrows;
                    }
                    //Se valida que el campo que se esta leyendo en el archivo de excel existe en la entidad
                    if (campoexiste == 0 && (c != null )) {
                        contf ++;
                        camponoexiste ++;
                        contfinalrows = contfinalrows + 1;
                    }else {
                        if (c != null ) {
                            contf ++;
                            contfinalrows = contfinalrows + 1;
                        }

                    }
                    centinelafin = centinelafin + 1;
                }

                //se actualizan los datos de cada registro del archivo de excel
                if ( entidad != null) {
                    this.PlasmidsService.savePlasmids(entidad);
                    //entidad_error.setObs("     Successfully updated records      ") ;
                    //  entidades_error.add(entidad_error);
                    entidades.add(entidad);

                    entidad = null;
                }

                //contc++;
                contfinalrows = 1;
                //  centinelafin = centinelafin + 1;
            }


            modelMap.addAttribute("entidades", entidades);

            modelMap.addAttribute("entidadesErr", entidades);

        }
        catch(IllegalArgumentException ile) {
            logger.error(ile.getLocalizedMessage());
            modelMap.addAttribute("importError", true);
            modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
            modelMap.addAttribute("entidades", entidades);
            return "capture/plasmids/uploadResulExcelt";
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            modelMap.addAttribute("importError", true);
            modelMap.addAttribute("errorMessage", e.getMessage());
            modelMap.addAttribute("entidades", entidades);
            return "capture/plasmids/uploadResultExcel";
        }

      /*  for(Specimen specimen:entidades) {
            MessageResource mr = null;
            String descCatalogo = null;
            mr = this.messageResourceService.getMensaje(specimen.getSpecimenType(),"CAT_SP_TYPE");
            if(mr!=null) {
                descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
                specimen.setSpecimenType(descCatalogo);
            }
        }*/

        modelMap.addAttribute("entidadesErr", entidades);

        return "capture/plasmids/uploadResultExcel";
    }

}
