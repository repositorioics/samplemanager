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
@RequestMapping("/capture/recombinantProtein/*")
public class CaptureRecombinantProtController {
    private static final Logger logger = LoggerFactory.getLogger(CaptureRecombinantProtController.class);

    @Resource(name="RecombinantProteinService")
    private RecombinantproteinService RecombinantproteinService;

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

    @Resource(name="RecombinantProteinFilterService")
    private RecombinantProteinFilterService  recombinantProteinFilterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException {
        logger.debug("Mostrando registros en JSP");


        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturerecombinantProteinpage");
        return "capture/recombinantProtein/list";
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

        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturenewrecombinantproteinpage");
        return "capture/recombinantProtein/enterNewForm";
    }

    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
    public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
        RecombinantProtein entidadEditar = this.RecombinantproteinService.getRecombninantProteinBySystemId(systemId) ;
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
            List <RecombinantProtein> specBox = this.RecombinantproteinService.getRecombninantProteinForBox(entidadEditar.getLoc_box().toString()) ;
            for(RecombinantProtein spec:specBox) {
                String posicion = String.valueOf(spec.getLoc_pos());
                posiciones.remove(posicion);
            }

            model.addAttribute("posiciones",posiciones);

            model.addAttribute("entidad",entidadEditar);
            List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
            model.addAttribute("substudies",substudies);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditrecombinantproteinpage");
            return "capture/recombinantProtein/editForm";
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
            , @RequestParam( value="protrecombinante_id", required=true) String protrecombinante_id
            , @RequestParam( value="protein_name", required=true) String protein_name
            , @RequestParam( value="protein_origin", required=false, defaultValue="") String protein_origin
            , @RequestParam( value="Construct_name", required=true) String Construct_name
            , @RequestParam( value="date_transfection", required=false, defaultValue="") String date_transfection
            , @RequestParam( value="vol_sn", required=false, defaultValue="") Integer vol_sn
            , @RequestParam( value="pur_method", required=false, defaultValue="") String pur_method
            , @RequestParam( value="frac_retained", required=false, defaultValue="") String frac_retained
            , @RequestParam( value="vol_usable", required=false, defaultValue="") String vol_usable
            , @RequestParam( value="dialysis_buffer", required=false, defaultValue="") String dialysis_buffer
            , @RequestParam( value="date_purification", required=true) String date_purification
            , @RequestParam( value="num_aliquot", required=false, defaultValue="") String num_aliquot
            , @RequestParam( value="vol_aliquot", required=false, defaultValue="") Float vol_aliquot
            , @RequestParam( value="conc_protein", required=false, defaultValue="") Float conc_protein

            , @RequestParam( value="loc_freezer", required=false, defaultValue="") String loc_freezer

            , @RequestParam( value="loc_rack", required=false, defaultValue="") String loc_rack
            , @RequestParam( value="loc_box", required=false) String loc_box
            , @RequestParam( value="loc_pos", required=false, defaultValue="") Integer loc_pos
            , @RequestParam( value="comments", required=false) String comments
    )
    {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso =  null;
            Date fechaAlmacenamiento =  null;
            WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

          //  RecombinantProtein entidad = this.RecombinantproteinService.getRecombninantProteinBySystemId(systemId) ;
            RecombinantProtein  entidad  = new RecombinantProtein(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!systemId.equals("")) entidad.setSystemId(systemId);

            if (!protrecombinante_id.equals("")) entidad.setProtrecombinante_id(protrecombinante_id) ;
            if (!protein_name.equals("")) entidad.setProtein_name(protein_name) ;
            if (!protein_origin.equals("")) entidad.setProtein_origin(protein_origin) ;
            if (!date_transfection.equals("")) {
                fechaIngreso=formatter.parse(date_transfection);
                entidad.setDate_transfection(fechaIngreso) ;
            }
            if (!Construct_name.equals("")) entidad.setConstruct_name(Construct_name) ;
            if (!vol_sn.equals("")) entidad.setVol_sn(Integer.valueOf(vol_sn)) ;
            if (!pur_method.equals("")) entidad.setPur_method((pur_method)) ;
            if (!frac_retained.equals("")) entidad.setFrac_retained(frac_retained) ;

            if (!vol_usable.equals("")) entidad.setVol_usable(Float.valueOf(vol_usable));
            if (!date_purification.equals("")) {
                fechaIngreso=formatter.parse(date_purification);
                entidad.setDate_purification(fechaIngreso); ;
            }


            if (!num_aliquot.equals("")) entidad.setNum_aliquot(num_aliquot);
            if (!vol_aliquot.equals("")) entidad.setVol_aliquot(vol_aliquot);
            if (!conc_protein.equals("")) entidad.setConc_protein(conc_protein);

            if (!loc_freezer.equals("")) entidad.setLoc_freezer(loc_freezer);



            if (!loc_rack.equals("")) entidad.setLoc_rack(loc_rack);

            if (!loc_box.equals("")) entidad.setLoc_box(loc_box); ;

            if (!loc_pos.equals(1) ) {
                if (!loc_pos.equals("")) entidad.setLoc_pos(loc_pos) ;
            }
            if (!comments.equals("")) entidad.setComments(comments);

            this.RecombinantproteinService.saveRecombinantProtein(entidad);

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
    private RecombinantProteinsFilters jsonToFilter(String strJson) throws Exception {
        JsonObject jObjectFiltro = new Gson().fromJson(strJson, JsonObject.class);
        RecombinantProteinsFilters antFilters = new RecombinantProteinsFilters();
        String protrecombinante_id = null;
        String protein_name = null;
        String protein_origin = null;
        String Construct_name = null;
        Date date_transfection = null;

        Integer activeSearch = null;

        if (jObjectFiltro.get("protrecombinante_id") != null && !jObjectFiltro.get("protrecombinante_id").getAsString().isEmpty())
            protrecombinante_id = jObjectFiltro.get("protrecombinante_id").getAsString();
        if (jObjectFiltro.get("protein_name") != null && !jObjectFiltro.get("protein_name").getAsString().isEmpty())
            protein_name = jObjectFiltro.get("protein_name").getAsString();
        if (jObjectFiltro.get("protein_origin") != null && !jObjectFiltro.get("protein_origin").getAsString().isEmpty())
            protein_origin = jObjectFiltro.get("protein_origin").getAsString();
        if (jObjectFiltro.get("Construct_name") != null && !jObjectFiltro.get("Construct_name").getAsString().isEmpty())
            Construct_name = jObjectFiltro.get("Construct_name").getAsString();


        if (jObjectFiltro.get("activeSearch") != null && !jObjectFiltro.get("activeSearch").getAsString().isEmpty())
            activeSearch = jObjectFiltro.get("activeSearch").getAsInt();


        antFilters.setProtrecombinante_id(protrecombinante_id);
        antFilters.setProtein_name(protein_name);
        antFilters.setProtein_origin(protein_origin);
        antFilters.setConstruct_name(Construct_name);
        antFilters.setDate_transfection(date_transfection);


        antFilters.setActiveSearch(activeSearch);

        return antFilters;
    }

    /**
     * Custom handler for searching.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/search/", method=RequestMethod.GET)
    public @ResponseBody List<RecombinantProteinResults> searchProcess(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
        RecombinantProteinsFilters specFilters = jsonToFilter(filtro);
        List<RecombinantProteinResults> recombinantProteinList;
        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");



        if(specFilters.getActiveSearch() == 0){
            recombinantProteinList = new ArrayList<>();
        }else {

            recombinantProteinList = recombinantProteinFilterService.getRecombinantProteinByFilter(specFilters) ;
        }
        String lang = LocaleContextHolder.getLocale().getLanguage();
        for(RecombinantProteinResults antib:recombinantProteinList) {
            String descCatalogo = null;

        }

        System.out.println("recombinantProteinList = "+ recombinantProteinList.toArray().toString());
        return recombinantProteinList ;
    }

    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
    public ResponseEntity<String> processEntity(@RequestParam( value="protrecombinante_id1", required=true ) String Protrecombinante_id

           , @RequestParam( value="protein_name2", required=true) String protein_name
           , @RequestParam( value="protein_origin3", required=false, defaultValue="") String protein_origin
           , @RequestParam( value="Construct_name4", required=true) String Construct_name
           , @RequestParam( value="date_transfection", required=false, defaultValue="") String date_transfection
           , @RequestParam( value="vol_sn5", required=false, defaultValue="") Integer vol_sn
           , @RequestParam( value="pur_method6", required=false, defaultValue="") String pur_method
           , @RequestParam( value="frac_retained7", required=false, defaultValue="") String frac_retained
           , @RequestParam( value="vol_usable8", required=false, defaultValue="") String vol_usable
           , @RequestParam( value="dialysis_buffer9", required=false, defaultValue="") String dialysis_buffer
           , @RequestParam( value="date_purification", required=true) String date_purification
           , @RequestParam( value="num_aliquot10", required=false, defaultValue="") String num_aliquot
           , @RequestParam( value="vol_aliquot11", required=false, defaultValue="") Float vol_aliquot
           , @RequestParam( value="conc_protein12", required=false, defaultValue="") Float conc_protein

           , @RequestParam( value="equip", required=false, defaultValue="") String equip

           , @RequestParam( value="rack", required=false, defaultValue="") String rack
           , @RequestParam( value="boxSpecId", required=false) String boxSpecId
           , @RequestParam( value="position", required=false, defaultValue="") Integer position
           , @RequestParam( value="comments", required=false) String comments
    )
    {
        try{
            System.out.println("Entra al controlador CaptureRecombinantProteinController");
            System.out.println("protrecombinante_id = "+ Protrecombinante_id);
            System.out.println("protein_name = " + protein_name);
            System.out.println("protein_origin = " +protein_origin);
            System.out.println("Construct_name = "+Construct_name);
            System.out.println("date_transfection = "+date_transfection);
            System.out.println("vol_sn = "+vol_sn);
            System.out.println("pur_method = "+pur_method);
            System.out.println("frac_retained = "+frac_retained);
            System.out.println("vol_usable = "+vol_usable);
            System.out.println("dialysis_buffer = "+dialysis_buffer);
            System.out.println("date_purification = "+date_purification);

            System.out.println("num_aliquot = "+num_aliquot);
            System.out.println("vol_aliquot = "+vol_aliquot);
            System.out.println("conc_protein = "+conc_protein);
            System.out.println("equip = "+equip);
            System.out.println("rack = "+rack);
            System.out.println("boxSpecId = "+boxSpecId);
            System.out.println("position = "+position);
            System.out.println("comments = "+comments);


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso =  null;
            Date fechaAlmacenamiento =  null;
            WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
           RecombinantProtein  entidad  = new RecombinantProtein(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
            SpecimenStorage entidad2 = null;
            //RecombinantProtein entidad = this.RecombinantproteinService.getRecombinantProteinByRecombinantProtein_id(protrecombinante_id) ;
          //  entidad  = new RecombinantProtein(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');



            if (!Protrecombinante_id.equals("")) entidad.setProtrecombinante_id(Protrecombinante_id) ;
            if (!protein_name.equals("")) entidad.setProtein_name(protein_name) ;
            if (!protein_origin.equals("")) entidad.setProtein_origin(protein_origin) ;
            if (!Construct_name.equals("")) entidad.setConstruct_name(Construct_name) ;
            if (!date_transfection.equals("")) {
                fechaIngreso=formatter.parse(date_transfection.toString());
                entidad.setDate_transfection(fechaIngreso) ;
            }

            if (!vol_sn.equals("")) entidad.setVol_sn(Integer.valueOf(vol_sn)) ;
            if (!pur_method.equals("")) entidad.setPur_method((pur_method)) ;
            if (!frac_retained.equals("")) entidad.setFrac_retained(frac_retained) ;
            if (!vol_usable.equals("")) entidad.setVol_usable(Float.valueOf(vol_usable)); ;

            if (!dialysis_buffer.equals("")) entidad.setDialysis_buffer(dialysis_buffer) ;
            if (!date_purification.equals("")) {
                fechaIngreso=formatter.parse(date_purification.toString());
                entidad.setDate_purification(fechaIngreso);
            }
            if (!num_aliquot.equals("")) entidad.setNum_aliquot(num_aliquot);
            if (!vol_aliquot.equals("")) entidad.setVol_aliquot(vol_aliquot);
            if (!conc_protein.equals("")) entidad.setConc_protein(conc_protein);

            if (!equip.equals("")) entidad.setLoc_freezer(equip);



            if (!rack.equals("")) entidad.setLoc_rack(rack);

            if (!boxSpecId.equals("")) entidad.setLoc_box(boxSpecId); ;

            if (!position.equals("") ) {
                if (!position.equals("")) entidad.setLoc_pos(position) ;
            }
            if (!comments.equals("")) entidad.setComments(comments);

            this.RecombinantproteinService.saveRecombinantProtein(entidad);

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
        return "capture/recombinantProtein/uploadExcel";
    }
    /**carga el archivo excel**/

    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
    public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        boolean checkLabReceiptDate = false;

         String protrecombinante_id;
         String protein_name;          //REQUIRED
         String protein_origin;       //REQUIRED
         String Construct_name;       //REQUIRED
         String date_transfection;
         Integer vol_sn;
         String pur_method;
         String frac_retained;
         Float vol_usable;
         String dialysis_buffer;      //REQUIRED
         String date_purification;
         String num_aliquot;          //REQUIRED
         Float vol_aliquot;           //REQUIRED
         Float conc_protein;          //REQUIRED
         String comments;

        String loc_freezer;               //REQUIRED
        String loc_rack;                 //REQUIRED
        String loc_box;                  //REQUIRED
        Integer loc_pos;                //REQUIRED


        String recordUser;
        String recordDate;
        String recordIp;

        int nuevos =0, viejos=0, id_no_validos=0;


        RecombinantProtein entidad = new RecombinantProtein();
        RecombinantProtein entidad_nuevos = new RecombinantProtein();
        List<RecombinantProtein> entidades = new ArrayList<RecombinantProtein>();
        List<RecombinantProtein> entidades_nuevos = new ArrayList<RecombinantProtein>();
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


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("protrecombinante_id")){
                            protrecombinante_id = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            entidad = this.RecombinantproteinService.getRecombinantProteinByRecombinantProtein_id(protrecombinante_id);
                            //   entidad_error = this.antibodyservice.getAntibodyByantibody_id(antibody_id);
                            if (entidad != null  ) {
                                entidad.setProtrecombinante_id(protrecombinante_id);
                                contfinalrows = contfinalrows + 1;
                                viejos = viejos + 1;
                            }else
                            {
                                if(!protrecombinante_id.toString().isEmpty()) {
                                    entidad.setProtrecombinante_id(protrecombinante_id);
                                    // entidades_nuevos.setAntibody_id(antibody_id);
                                    nuevos = nuevos + 1;
                                }
                                else {
                                    id_no_validos =  id_no_validos + 1 ;
                                }

                            }

                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("protein_name")){
                            protein_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setProtein_name(protein_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!protein_name.toString().isEmpty()) {
                                    entidad.setProtein_name(protein_name);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("protein_origin")){
                            protein_origin = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setProtein_origin(protein_origin);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!protein_origin.toString().isEmpty()) {
                                    entidad.setProtein_origin(protein_origin);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("Construct_name")){
                            Construct_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setConstruct_name(Construct_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!Construct_name.toString().isEmpty()) {
                                    entidad.setConstruct_name(Construct_name);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("date_transfection")){
                            date_transfection = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                            Date fecha2 = formato.parse(date_transfection);
                            if (entidad != null  ) {
                                entidad.setDate_transfection(fecha2);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!date_transfection.toString().isEmpty()) {
                                    entidad.setDate_transfection(fecha2);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("vol_sn")){
                            vol_sn = Integer.parseInt(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setVol_sn(vol_sn);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!vol_sn.toString().isEmpty()) {
                                    entidad.setVol_sn(vol_sn);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("pur_method")){
                            pur_method = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setPur_method(pur_method);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!pur_method.toString().isEmpty()) {
                                    entidad.setPur_method(pur_method);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("frac_retained")){
                            frac_retained = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setFrac_retained(frac_retained);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!frac_retained.toString().isEmpty()) {
                                    entidad.setFrac_retained(frac_retained);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("vol_usable")){
                            vol_usable = Float.parseFloat(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setVol_usable(vol_usable);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!vol_usable.toString().isEmpty()) {
                                    entidad.setVol_usable(vol_usable);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("dialysis_buffer")){
                            dialysis_buffer = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();

                            if (entidad != null  ) {
                                entidad.setDialysis_buffer(dialysis_buffer);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!dialysis_buffer.toString().isEmpty()) {
                                    entidad.setDialysis_buffer(dialysis_buffer);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("date_purification")){
                            date_purification = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                            Date fecha2 = formato.parse(date_purification);
                            if (entidad != null  ) {
                                entidad.setDate_purification(fecha2);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!date_purification.toString().isEmpty()) {
                                    entidad.setDate_purification(fecha2);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("num_aliquot")){
                            num_aliquot = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();

                            if (entidad != null  ) {
                                entidad.setNum_aliquot(num_aliquot);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!num_aliquot.toString().isEmpty()) {
                                    entidad.setNum_aliquot(num_aliquot);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("vol_aliquot")){
                            vol_aliquot = Float.parseFloat(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setVol_aliquot(vol_aliquot);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!vol_aliquot.toString().isEmpty()) {
                                    entidad.setVol_aliquot(vol_aliquot);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("conc_protein")){
                            conc_protein = Float.parseFloat(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setConc_protein(conc_protein);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!conc_protein.toString().isEmpty()) {
                                    entidad.setConc_protein(conc_protein);
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
                    this.RecombinantproteinService.saveRecombinantProtein(entidad);
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
            return "capture/recombinantProtein/uploadResulExcelt";
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            modelMap.addAttribute("importError", true);
            modelMap.addAttribute("errorMessage", e.getMessage());
            modelMap.addAttribute("entidades", entidades);
            return "capture/recombinantProtein/uploadResultExcel";
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

        return "capture/recombinantProtein/uploadResultExcel";
    }


}
