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
@RequestMapping("/capture/primers/*")
public class CapturePrimersController {
    private static final Logger logger = LoggerFactory.getLogger(CapturePrimersController.class);

    @Resource(name="PrimersService")
    private PrimersService PrimersService;

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

    @Resource(name="PrimersFilterService")
    private PrimersFilterService  PrimersFilterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException {
        logger.debug("Mostrando registros en JSP");


        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureprimerspage");
        return "capture/primers/list";
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
        return "capture/primers/enterNewForm";
    }

    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
    public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
        Primers entidadEditar = this.PrimersService.getPrimersBySystemId(systemId) ;
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
            List <Primers> specBox = this.PrimersService.getPrimersForBox(entidadEditar.getLoc_box().toString()) ;
            for(Primers spec:specBox) {
                String posicion = String.valueOf(spec.getLoc_pos());
                posiciones.remove(posicion);
            }

            model.addAttribute("posiciones",posiciones);

            model.addAttribute("entidad",entidadEditar);
            List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
            model.addAttribute("substudies",substudies);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditprimerspage");
            return "capture/primers/editForm";
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
            , @RequestParam( value="primers_id", required=true) String primers_id
            , @RequestParam( value="primer_number", required=true) Integer primer_number
            , @RequestParam( value="position_in_sequence", required=false, defaultValue="") String position_in_sequence
            , @RequestParam( value="date_received", required=true) Date date_received
            , @RequestParam( value="primer_name", required=false, defaultValue="") String primer_name
            , @RequestParam( value="primer_description", required=false, defaultValue="") String primer_description
            , @RequestParam( value="primer_sequence", required=false, defaultValue="") String primer_sequence
            , @RequestParam( value="primer_lenght", required=false, defaultValue="") Integer primer_lenght
            , @RequestParam( value="primer_designer", required=false, defaultValue="") String primer_designer
            , @RequestParam( value="comments", required=false, defaultValue="") String comments
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

            Primers  entidad  = new Primers(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!systemId.equals("")) entidad.setSystemId(systemId);

            if (!primers_id.equals("")) entidad.setPrimers_id(primers_id);
            if (!primer_number.equals("")) entidad.setPrimer_number(Integer.valueOf( primer_number));
            if (!position_in_sequence.equals("")) entidad.setPosition_in_sequence(position_in_sequence);


            if (!date_received.equals("")) entidad.setDate_received(date_received); ;
            if (!primer_name.equals("")) entidad.setPrimer_name(primer_name); ;
            if (!primer_description.equals("")) entidad.setPrimer_description((primer_description)); ;
            if (!primer_sequence.equals("")) entidad.setPrimer_sequence(primer_sequence); ;

            if (!primer_lenght.equals("")) entidad.setPrimer_lenght(Integer.valueOf(primer_lenght));

            if (!primer_designer.equals("")) entidad.setPrimer_designer(primer_designer);

            if (!comments.equals("")) entidad.setComments(comments);

            if (!loc_freezer.equals("")) entidad.setLoc_freezer(loc_freezer);

            if (!loc_rack.equals("")) entidad.setLoc_rack(loc_rack);

            if (!loc_box.equals("")) entidad.setLoc_box(loc_box); ;

            if (!loc_pos.equals(1) ) {
                if (!loc_pos.equals("")) entidad.setLoc_pos(loc_pos) ;
            }


            this.PrimersService.savePrimers(entidad);

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
    private PrimersFilters jsonToFilter(String strJson) throws Exception {
        JsonObject jObjectFiltro = new Gson().fromJson(strJson, JsonObject.class);
        PrimersFilters antFilters = new PrimersFilters();
        String primers_id = null;
        Integer primer_number = null;
        String primer_name = null;
        String primer_sequence = null;


        Integer activeSearch = null;

        if (jObjectFiltro.get("primers_id") != null && !jObjectFiltro.get("primers_id").getAsString().isEmpty())
            primers_id = jObjectFiltro.get("primers_id").getAsString();
        if (jObjectFiltro.get("primer_number") != null && !jObjectFiltro.get("primer_number").getAsString().isEmpty())
            primer_number = jObjectFiltro.get("primer_number").getAsInt();
        if (jObjectFiltro.get("primer_name") != null && !jObjectFiltro.get("primer_name").getAsString().isEmpty())
            primer_name = jObjectFiltro.get("primer_name").getAsString();
        if (jObjectFiltro.get("primer_sequence") != null && !jObjectFiltro.get("primer_sequence").getAsString().isEmpty())
            primer_sequence = jObjectFiltro.get("primer_sequence").getAsString();


        if (jObjectFiltro.get("activeSearch") != null && !jObjectFiltro.get("activeSearch").getAsString().isEmpty())
            activeSearch = jObjectFiltro.get("activeSearch").getAsInt();


        antFilters.setPrimers_id(primers_id);
        antFilters.setPrimer_number(primer_number);
        antFilters.setPrimer_name(primer_name);
        antFilters.setPrimer_sequence(primer_sequence);

        antFilters.setActiveSearch(activeSearch);

        return antFilters;
    }

    /**
     * Custom handler for searching.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/search/", method=RequestMethod.GET)
    public @ResponseBody List<PrimersResults> searchProcess(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
        PrimersFilters specFilters = jsonToFilter(filtro);
        List<PrimersResults> primersList;
        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");



        if(specFilters.getActiveSearch() == 0){
            primersList = new ArrayList<>();
        }else {

            primersList = PrimersFilterService.getPlasmidsByFilter(specFilters) ;
        }
        String lang = LocaleContextHolder.getLocale().getLanguage();
        for(PrimersResults antib:primersList) {
            String descCatalogo = null;

        }

        System.out.println("primersList = "+ primersList.toArray().toString());
        return primersList ;
    }

    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
    public ResponseEntity<String> processEntity(@RequestParam( value="primers_id", required=false ) String primers_id

            , @RequestParam( value="primer_number", required=false) Integer primer_number
            , @RequestParam( value="position_in_sequence", required=false, defaultValue="") String position_in_sequence
            , @RequestParam( value="date_received", required=false, defaultValue="") String date_received
            , @RequestParam( value="primer_name1", required=false, defaultValue="") String primer_name1
            , @RequestParam( value="primer_description", required=false, defaultValue="") String primer_description
            , @RequestParam( value="primer_sequence", required=false, defaultValue="") String primer_sequence
            , @RequestParam( value="primer_lenght", required=false, defaultValue="") Integer primer_lenght
            , @RequestParam( value="primer_designer", required=false, defaultValue="") String primer_designer
            , @RequestParam( value="comments", required=false, defaultValue="") String comments
            , @RequestParam( value="equip", required=false, defaultValue="") String equip
            , @RequestParam( value="rack", required=false, defaultValue="") String rack
            , @RequestParam( value="boxSpecId", required=false) String boxSpecId
            , @RequestParam( value="position", required=false, defaultValue="") Integer position

    )
    {
        try{

            System.out.print("Entra SaveEntity  ");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso =  null;
            Date fechaAlmacenamiento =  null;
            WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
           Primers  entidad  = new Primers(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
            SpecimenStorage entidad2 = null;
            //RecombinantProtein entidad = this.RecombinantproteinService.getRecombinantProteinByRecombinantProtein_id(protrecombinante_id) ;
          //  entidad  = new RecombinantProtein(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!primers_id.equals("")) entidad.setPrimers_id(primers_id);
            if (!primer_number.equals("")) entidad.setPrimer_number(Integer.valueOf( primer_number));
            if (!position_in_sequence.equals("")) entidad.setPosition_in_sequence(position_in_sequence);


            if (!date_received.equals("")) {
                fechaIngreso=formatter.parse(date_received);
                entidad.setDate_received(fechaIngreso); }

            if (!primer_name1.equals("")) entidad.setPrimer_name(primer_name1);
            if (!primer_description.equals("")) entidad.setPrimer_description((primer_description));
            if (!primer_sequence.equals("")) entidad.setPrimer_sequence(primer_sequence);

            if (!primer_lenght.equals("")) entidad.setPrimer_lenght(Integer.valueOf(primer_lenght));

            if (!primer_designer.equals("")) entidad.setPrimer_designer(primer_designer);

            if (!comments.equals("")) entidad.setComments(comments);

            if (!equip.equals("")) entidad.setLoc_freezer(equip);

            if (!rack.equals("")) entidad.setLoc_rack(rack);

            if (!boxSpecId.equals("")) entidad.setLoc_box(boxSpecId); ;

            if (!position.equals(1) ) {
                if (!position.equals("")) entidad.setLoc_pos(position) ;
            }

            this.PrimersService.savePrimers(entidad);

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
        return "capture/primers/uploadExcel";
    }
    /**carga el archivo excel**/

    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
    public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        boolean checkLabReceiptDate = false;

         String primers_id;      //REQUIRED
         Integer primer_number;   //REQUIRED
         String position_in_sequence;
         String date_received;
         String primer_name;          //REQUIRED
         String primer_description;   //REQUIRED
         String primer_sequence;      //REQUIRED
         Integer primer_lenght;
         String primer_designer;
         String comments;

         String loc_freezer;               //REQUIRED
         String loc_rack;                 //REQUIRED
         String loc_box;                  //REQUIRED
         Integer loc_pos;                //REQUIRED


        String recordUser;
        String recordDate;
        String recordIp;

        int nuevos =0, viejos=0, id_no_validos=0;


        Primers entidad = new Primers();
        Primers entidad_nuevos = new Primers();
        List<Primers> entidades = new ArrayList<Primers>();
        List<Primers> entidades_nuevos = new ArrayList<Primers>();
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


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("primers_id")){
                            primers_id = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            entidad = this.PrimersService.getPrimersByPrimers_id(primers_id);
                            //   entidad_error = this.antibodyservice.getAntibodyByantibody_id(antibody_id);
                            if (entidad != null  ) {
                                entidad.setPrimers_id(primers_id);
                                contfinalrows = contfinalrows + 1;
                                viejos = viejos + 1;
                            }else
                            {
                                if(!primers_id.toString().isEmpty()) {
                                    entidad.setPrimers_id(primers_id);
                                    // entidades_nuevos.setAntibody_id(antibody_id);
                                    nuevos = nuevos + 1;
                                }
                                else {
                                    id_no_validos =  id_no_validos + 1 ;
                                }

                            }

                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("primer_number")){
                            primer_number = Integer.parseInt(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setPrimer_number(primer_number);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!primer_number.toString().isEmpty()) {
                                    entidad.setPrimer_number(primer_number);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("position_in_sequence")){
                            position_in_sequence = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setPosition_in_sequence(position_in_sequence);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!position_in_sequence.toString().isEmpty()) {
                                    entidad.setPosition_in_sequence(position_in_sequence);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("date_received")){
                            date_received = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                            Date fecha2 = formato.parse(date_received);
                            if (entidad != null  ) {
                                entidad.setDate_received(fecha2);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!date_received.toString().isEmpty()) {
                                    entidad.setDate_received(fecha2);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("primer_name")){
                            primer_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setPrimer_name(primer_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!primer_name.toString().isEmpty()) {
                                    entidad.setPrimer_name(primer_name);
                                }
                            }
                        }



                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("primer_description")){
                            primer_description = (sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setPrimer_description(primer_description);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!primer_description.toString().isEmpty()) {
                                    entidad.setPrimer_description(primer_description);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("primer_sequence")){
                            primer_sequence = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setPrimer_sequence(primer_sequence);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!primer_sequence.toString().isEmpty()) {
                                    entidad.setPrimer_sequence(primer_sequence);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("primer_lenght")){
                            primer_lenght = Integer.parseInt(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setPrimer_lenght(primer_lenght);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!primer_lenght.toString().isEmpty()) {
                                    entidad.setPrimer_lenght(primer_lenght);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("primer_designer")){
                            primer_designer = (sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setPrimer_designer(primer_designer);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!primer_designer.toString().isEmpty()) {
                                    entidad.setPrimer_designer(primer_designer);
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
                    this.PrimersService.savePrimers(entidad);
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
            return "capture/primers/uploadResulExcelt";
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            modelMap.addAttribute("importError", true);
            modelMap.addAttribute("errorMessage", e.getMessage());
            modelMap.addAttribute("entidades", entidades);
            return "capture/primers/uploadResultExcel";
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

        return "capture/primers/uploadResultExcel";
    }
}
