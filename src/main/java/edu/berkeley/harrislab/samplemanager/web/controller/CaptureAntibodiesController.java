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
@RequestMapping("/capture/antibodies/*")
public class CaptureAntibodiesController {
    private static final Logger logger = LoggerFactory.getLogger(CaptureAntibodiesController.class);
    @Resource(name="AntibodyService")
    private AntibodyService AntibodyService;

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
    @Resource(name="AntibodyService")
    private AntibodyService antibodyservice;

    @Resource(name="usuarioService")
    private UsuarioService usuarioService;

    @Resource(name="visitsService")
    private VisitsService visitsService;

    @Resource(name="antibodyFilterService")
    private AntibodyFilterService  antibodyFilterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException {
        logger.debug("Mostrando registros en JSP");


        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureantibodiespage");
        return "capture/antibodies/list_1";
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

        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturenewantibodypage");
        return "capture/antibodies/enterNewForm";
    }

    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
    public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
        Antibody entidadEditar = this.AntibodyService.getAntibodyBySystemId(systemId);
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

            List<Rack> racks = this.rackService.getRacks(entidadEditar.getFreezer_name().toString());
            model.addAttribute("racks",racks);

            List<Box> boxes = this.boxService.getActiveBoxes(entidadEditar.getRack_name().toString());
            model.addAttribute("boxes",boxes);

            List<String> posiciones = new ArrayList<String>();
            Box box = this.boxService.getBoxBySystemId(entidadEditar.getBox_name().toString());
            if (box!=null){
                for(Integer i = 1; i<=box.getCapacity(); i++){
                    posiciones.add(i.toString());
                }
            }
            List <Antibody> specBox = this.AntibodyService.getAntibodyForBox(entidadEditar.getBox_name().toString());
            for(Antibody spec:specBox) {
                String posicion = String.valueOf(spec.getPosition_in_the_box());
                posiciones.remove(posicion);
            }

            model.addAttribute("posiciones",posiciones);

            model.addAttribute("entidad",entidadEditar);
            List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
            model.addAttribute("substudies",substudies);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditspecimenpage");
            return "capture/antibodies/editForm";
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
            , @RequestParam( value="antibody_id", required=true) String antibody_id
            , @RequestParam( value="antibody_name", required=true) String antibody_name
            , @RequestParam( value="target_protein", required=false, defaultValue="") String target_protein
            , @RequestParam( value="target_domain", required=true) String target_domain
            , @RequestParam( value="target_epitope", required=false, defaultValue="") String target_epitope
            , @RequestParam( value="antibody_isotype", required=false, defaultValue="") String antibody_isotype
            , @RequestParam( value="recognizes", required=false, defaultValue="") String recognizes
            , @RequestParam( value="batch_number", required=false, defaultValue="") String batch_number
            , @RequestParam( value="date_produced", required=false, defaultValue="") String date_produced
            , @RequestParam( value="person_name", required=false, defaultValue="") String person_name
            , @RequestParam( value="source_name", required=true) String source_name
            , @RequestParam( value="raised_in", required=true) String raised_in
            , @RequestParam( value="aliquot_volume", required=false, defaultValue="") Float aliquot_volume
            , @RequestParam( value="medium_buffer", required=false, defaultValue="") String medium_buffer
            , @RequestParam( value="concentration", required=false, defaultValue="") String concentration
            , @RequestParam( value="technique1", required=false, defaultValue="") String technique1
            , @RequestParam( value="technique1_concentration1", required=false) String technique1_concentration1
            , @RequestParam( value="technique2", required=false, defaultValue="") String technique2
            , @RequestParam( value="technique2_concentration2", required=false) String technique2_concentration2
            , @RequestParam( value="technique3", required=false, defaultValue="") String technique3
            , @RequestParam( value="technique3_concentration3", required=false) String technique3_concentration3
            , @RequestParam( value="storage_temperature", required=false, defaultValue="") String storage_temperature
            , @RequestParam( value="equip", required=false, defaultValue="") String equip

            , @RequestParam( value="rack", required=false, defaultValue="") String rack
            , @RequestParam( value="boxSpecId", required=false) String boxSpecId
            , @RequestParam( value="positions", required=false, defaultValue="") Integer positions
            , @RequestParam( value="additional_comments", required=false) String additional_comments
    )
    {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso =  null;
            Date fechaAlmacenamiento =  null;
            WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
           // Antibody  entidad  = new Antibody(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
            SpecimenStorage entidad2 = null;

            Antibody entidad = this.AntibodyService.getAntibodyBySystemId(systemId);
            if (!systemId.equals("")) entidad.setSystemId(systemId);

            if (!antibody_id.equals("")) entidad.setAntibody_id(antibody_id) ;
            if (!antibody_name.equals("")) entidad.setAntibody_name(antibody_name) ;
            if (!target_protein.equals("")) entidad.setTarget_protein(target_protein) ;
            if (!date_produced.equals("")) {
                fechaIngreso=formatter.parse(date_produced);
                entidad.setDate_produced(fechaIngreso);
            }
            if (!target_domain.equals("")) entidad.setTarget_domain(target_domain);
            if (!target_epitope.equals("")) entidad.setTarget_epitope(target_epitope);
            if (!aliquot_volume.equals("")) entidad.setAliquot_volume(Float.valueOf(aliquot_volume)) ;
            if (!antibody_isotype.equals("")) entidad.setAntibody_isotype(antibody_isotype);
           // if (!position.equals("")) entidad.setPosition_in_the_box(Integer.valueOf(position));
            if (!recognizes.equals("")) entidad.setRecognizes(recognizes);
            if (!batch_number.equals("")) entidad.setBatch_number(batch_number);
            if (!person_name.equals("")) entidad.setPerson_name(person_name);
            if (!source_name.equals("")) entidad.setSource_name(source_name);
            if (!raised_in.equals("")) entidad.setRaised_in(raised_in);

            if (!concentration.equals("")) entidad.setConcentration(concentration);
            if (!technique1.equals("")) entidad.setTechnique1(technique1);
            if (!technique1_concentration1.equals("")) entidad.setTechnique1_concentration1(technique1_concentration1);
            if (!technique2.equals("")) entidad.setTechnique2(technique2);
            if (!technique2_concentration2.equals("")) entidad.setTechnique2_concentration2(technique2_concentration2);
            if (!technique3.equals("")) entidad.setTechnique3(technique3);
            if (!technique3_concentration3.equals("")) entidad.setTechnique3_concentration3(technique3_concentration3);
            if (!storage_temperature.equals("")) entidad.setStorage_temperature(storage_temperature);
            if (!equip.equals("")) entidad.setFreezer_name(equip);

            System.out.println(" positions: "+positions.toString());
            System.out.println(" rack: "+rack.toString());
            System.out.println(" boxSpecId: "+boxSpecId.toString());
            System.out.println(" equip: "+equip.toString());

            if (!rack.equals("")) entidad.setRack_name(rack);

            if (!boxSpecId.equals("")) entidad.setBox_name(boxSpecId) ;

            if (!equip.equals("")) entidad.setFreezer_name(equip);

            // if (!rack.equals("")) entidad.setRack_name(this.rackService.getRackBySystemId(rack).getName());
            if (!rack.equals("")) entidad.setRack_name(rack);

            // if (!boxSpecId.equals("")) entidad.setbox_name(this.boxService.getBoxBySystemId(boxSpecId).getName()) ;
            if (!boxSpecId.equals("")) entidad.setBox_name(boxSpecId) ;

            if (!positions.equals(1) ) {
                if (!positions.equals("")) entidad.setPosition_in_the_box(positions);
            }
            if (!additional_comments.equals("")) entidad.setAdditional_comments(additional_comments);

            this.AntibodyService.saveAntibody(entidad);

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
    private AntibodysFilters jsonToFilter(String strJson) throws Exception {
        JsonObject jObjectFiltro = new Gson().fromJson(strJson, JsonObject.class);
        AntibodysFilters antFilters = new AntibodysFilters();
        String antibody_id = null;
        String antibody_name = null;
        Integer activeSearch = null;

        if (jObjectFiltro.get("antibody_id") != null && !jObjectFiltro.get("antibody_id").getAsString().isEmpty())
            antibody_id = jObjectFiltro.get("antibody_id").getAsString();
        if (jObjectFiltro.get("antibody_name") != null && !jObjectFiltro.get("antibody_name").getAsString().isEmpty())
            antibody_name = jObjectFiltro.get("antibody_name").getAsString();


        if (jObjectFiltro.get("activeSearch") != null && !jObjectFiltro.get("activeSearch").getAsString().isEmpty())
            activeSearch = jObjectFiltro.get("activeSearch").getAsInt();


        antFilters.setantibody_id(antibody_id);
        antFilters.setantibody_name(antibody_name);

        antFilters.setActiveSearch(activeSearch);

        return antFilters;
    }

    /**
     * Custom handler for searching.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/search/", method=RequestMethod.GET)
    public @ResponseBody List<AntibodiesResults> searchProcess(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
        AntibodysFilters specFilters = jsonToFilter(filtro);
        List<AntibodiesResults> antibodyList;
        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");

        if(specFilters.getActiveSearch() == 0){
            antibodyList = new ArrayList<>();
        }else {
            antibodyList = antibodyFilterService.getAntibodyByFilter(specFilters);
        }
        String lang = LocaleContextHolder.getLocale().getLanguage();
        for(AntibodiesResults antib:antibodyList) {
            String descCatalogo = null;

        }
        return antibodyList ;
    }

   @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
    public ResponseEntity<String> processEntity(@RequestParam( value="antibody_id", required=true ) String antibody_id

            , @RequestParam( value="antibody_name", required=true) String antibody_name
            , @RequestParam( value="target_protein", required=false, defaultValue="") String target_protein
            , @RequestParam( value="target_domain", required=true) String target_domain
            , @RequestParam( value="target_epitope", required=false, defaultValue="") String target_epitope
            , @RequestParam( value="antibody_isotype", required=false, defaultValue="") String antibody_isotype
            , @RequestParam( value="recognizes", required=false, defaultValue="") String recognizes
            , @RequestParam( value="batch_number", required=false, defaultValue="") String batch_number
            , @RequestParam( value="date_produced", required=false, defaultValue="") String date_produced
            , @RequestParam( value="person_name", required=false, defaultValue="") String person_name
            , @RequestParam( value="source_name", required=true) String source_name
            , @RequestParam( value="raised_in", required=true) String raised_in
            , @RequestParam( value="aliquot_volume", required=false, defaultValue="") Float aliquot_volume
            , @RequestParam( value="medium_buffer", required=false, defaultValue="") String medium_buffer
            , @RequestParam( value="concentration", required=false, defaultValue="") String concentration
            , @RequestParam( value="technique1", required=false, defaultValue="") String technique1
            , @RequestParam( value="technique1_concentration1", required=false) String technique1_concentration1
            , @RequestParam( value="technique2", required=false, defaultValue="") String technique2
            , @RequestParam( value="technique2_concentration2", required=false) String technique2_concentration2
            , @RequestParam( value="technique3", required=false, defaultValue="") String technique3
            , @RequestParam( value="technique3_concentration3", required=false) String technique3_concentration3
            , @RequestParam( value="storage_temperature", required=false, defaultValue="") String storage_temperature
            , @RequestParam( value="equip", required=false, defaultValue="") String equip

            , @RequestParam( value="rack", required=false, defaultValue="") String rack
            , @RequestParam( value="boxSpecId", required=false) String boxSpecId
            , @RequestParam( value="position", required=false, defaultValue="") Integer position
            , @RequestParam( value="additional_comments", required=false) String additional_comments
    )
    {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaIngreso =  null;
            Date fechaAlmacenamiento =  null;
            WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            Antibody  entidad  = new Antibody(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
            SpecimenStorage entidad2 = null;
            if (!antibody_id.equals("")) entidad.setAntibody_id(antibody_id) ;
            if (!antibody_name.equals("")) entidad.setAntibody_name(antibody_name) ;
            if (!target_protein.equals("")) entidad.setTarget_protein(target_protein) ;
            if (!date_produced.equals("")) {
                fechaIngreso=formatter.parse(date_produced);
                entidad.setDate_produced(fechaIngreso);
            }
            if (!target_domain.equals("")) entidad.setTarget_domain(target_domain);
            if (!target_epitope.equals("")) entidad.setTarget_epitope(target_epitope);
            if (!aliquot_volume.equals("")) entidad.setAliquot_volume(Float.valueOf(aliquot_volume)) ;
            if (!medium_buffer.equals("")) entidad.setMedium_buffer(medium_buffer);
            if (!antibody_isotype.equals("")) entidad.setAntibody_isotype(antibody_isotype);
            if (!position.equals("")) entidad.setPosition_in_the_box(Integer.valueOf(position));
            if (!recognizes.equals("")) entidad.setRecognizes(recognizes);
            if (!batch_number.equals("")) entidad.setBatch_number(batch_number);
            if (!person_name.equals("")) entidad.setPerson_name(person_name);
            if (!source_name.equals("")) entidad.setSource_name(source_name);
            if (!raised_in.equals("")) entidad.setRaised_in(raised_in);

            if (!concentration.equals("")) entidad.setConcentration(concentration);
            if (!technique1.equals("")) entidad.setTechnique1(technique1);
            if (!technique1_concentration1.equals("")) entidad.setTechnique1_concentration1(technique1_concentration1);
            if (!technique2.equals("")) entidad.setTechnique2(technique2);
            if (!technique2_concentration2.equals("")) entidad.setTechnique2_concentration2(technique2_concentration2);
            if (!technique3.equals("")) entidad.setTechnique3(technique3);
            if (!technique3_concentration3.equals("")) entidad.setTechnique3_concentration3(technique3_concentration3);

            if (!storage_temperature.equals("")) entidad.setStorage_temperature(storage_temperature);
            //if (!equip.equals("")) entidad.setFreezer_name(this.equipService.getEquipBySystemId(equip).getName() );
            if (!equip.equals("")) entidad.setFreezer_name(equip);

           // if (!rack.equals("")) entidad.setRack_name(this.rackService.getRackBySystemId(rack).getName());
            if (!rack.equals("")) entidad.setRack_name(rack);

           // if (!boxSpecId.equals("")) entidad.setbox_name(this.boxService.getBoxBySystemId(boxSpecId).getName()) ;
            if (!boxSpecId.equals("")) entidad.setBox_name(boxSpecId) ;

            if (!position.equals("")) entidad.setPosition_in_the_box(position);
            if (!additional_comments.equals("")) entidad.setAdditional_comments(additional_comments);

                this.AntibodyService.saveAntibody(entidad);

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
        return "capture/antibodies/uploadExcel";
    }
    /**carga el archivo excel**/

    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
    public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        boolean checkLabReceiptDate = false;

         String antibody_id;   //requerido
         String antibody_name; //requerido
         String target_protein=""; //requerido
         String target_domain;
         String target_epitope="";
         String antibody_isotype;
         String recognizes;
         String batch_number;
         String date_produced;
         String person_name;
         String source_name;     //requerido
         String raised_in="";      //requerido
         Float aliquot_volume;   //requerido
         String medium_buffer;
         String concentration;
         String technique1;                 //requerido
         String technique1_concentration1;   //requerido
         String technique2;
         String technique2_concentration2;
         String technique3;
         String technique3_concentration3;
         String storage_temperature;          //requerido
         String freezer_name;                 //requerido

         String rack_name;                    //requerido
         String box_name;                     //requerido
         Integer position;                    //requerido
         String additional_comments;

         String recordUser;
         String recordDate;
        int nuevos =0, viejos=0, id_no_validos=0;


        Antibody entidad = new Antibody();
        Antibody entidad_nuevos = new Antibody();
        List<Antibody> entidades = new ArrayList<Antibody>();
        List<Antibody> entidades_nuevos = new ArrayList<Antibody>();
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


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("antibody_id")){
                            antibody_id = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            entidad = this.antibodyservice.getAntibodyByantibody_id(antibody_id);
                         //   entidad_error = this.antibodyservice.getAntibodyByantibody_id(antibody_id);
                            if (entidad != null  ) {
                                entidad.setAntibody_id(antibody_id);
                                contfinalrows = contfinalrows + 1;
                                viejos = viejos + 1;
                            }else
                            {
                               if(!antibody_id.toString().isEmpty()) {
                                   entidad.setAntibody_id(antibody_id);
                                  // entidades_nuevos.setAntibody_id(antibody_id);
                                   nuevos = nuevos + 1;
                               }
                                else {
                                   id_no_validos =  id_no_validos + 1 ;
                               }

                            }

                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("antibody_name")){
                            antibody_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setAntibody_name(antibody_name) ;
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!antibody_name.toString().isEmpty()) {
                                    entidad.setAntibody_name(antibody_name) ;
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("target_protein")){
                            target_protein = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTarget_protein(target_protein);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!target_protein.toString().isEmpty()) {
                                    entidad.setTarget_protein(target_protein);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("target_domain")){
                            target_domain = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTarget_domain(target_domain);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!target_protein.toString().isEmpty()) {
                                    entidad.setTarget_domain(target_domain);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("target_epitope")){
                            target_epitope = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTarget_epitope(target_epitope);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!target_epitope.toString().isEmpty()) {
                                    entidad.setTarget_epitope(target_epitope);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("antibody_isotype")){
                            antibody_isotype = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setAntibody_isotype(antibody_isotype);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!target_epitope.toString().isEmpty()) {
                                    entidad.setAntibody_isotype(antibody_isotype);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("recognizes")){
                            recognizes = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setRecognizes(recognizes);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!recognizes.toString().isEmpty()) {
                                    entidad.setRecognizes(recognizes);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("batch_number")){
                            batch_number = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setBatch_number(batch_number);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!batch_number.toString().isEmpty()) {
                                    entidad.setBatch_number(batch_number);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("date_produced")){
                            date_produced = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                            Date fecha1 = formato.parse(date_produced);
                            if (entidad != null  ) {
                                entidad.setDate_produced(fecha1);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!date_produced.toString().isEmpty()) {
                                    entidad.setDate_produced(fecha1);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("person_name")){
                            person_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setPerson_name(person_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!person_name.toString().isEmpty()) {
                                    entidad.setPerson_name(person_name);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("source_name")){
                            source_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setSource_name(source_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!source_name.toString().isEmpty()) {
                                    entidad.setSource_name(source_name);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("raised_in")){
                            raised_in = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setRaised_in(raised_in);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!raised_in.toString().isEmpty()) {
                                    entidad.setRaised_in(raised_in);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("aliquot_volume")){
                            aliquot_volume = Float.parseFloat(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            if (entidad != null  ) {
                                entidad.setAliquot_volume((aliquot_volume));
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!aliquot_volume.toString().isEmpty()) {
                                    entidad.setAliquot_volume((aliquot_volume));
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("medium_buffer")){
                            medium_buffer = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setMedium_buffer(medium_buffer);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!raised_in.toString().isEmpty()) {
                                    entidad.setMedium_buffer(medium_buffer);
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

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("technique1_concentration1")){
                            technique1_concentration1 = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTechnique1_concentration1(technique1_concentration1);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!technique1_concentration1.toString().isEmpty()) {
                                    entidad.setTechnique1_concentration1(technique1_concentration1);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("technique2")){
                            technique2 = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTechnique2(technique2);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!technique2.toString().isEmpty()) {
                                    entidad.setTechnique2(technique2);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("technique2_concentration2")){
                            technique2_concentration2 = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTechnique2_concentration2(technique2_concentration2);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!technique2_concentration2.toString().isEmpty()) {
                                    entidad.setTechnique2_concentration2(technique2_concentration2);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("technique3")){
                            technique3 = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTechnique3(technique3);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!technique3.toString().isEmpty()) {
                                    entidad.setTechnique3(technique3);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("technique3_concentration3")){
                            technique3_concentration3 = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setTechnique3_concentration3(technique3_concentration3);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!technique3_concentration3.toString().isEmpty()) {
                                    entidad.setTechnique3_concentration3(technique3_concentration3);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("storage_temperature")){
                            storage_temperature = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null  ) {
                                entidad.setStorage_temperature(storage_temperature);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!storage_temperature.toString().isEmpty()) {
                                    entidad.setStorage_temperature(storage_temperature);
                                }
                            }
                        }


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("freezer_name")){
                            freezer_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            freezer_name = equipService.getEquipByUserId(freezer_name).getSystemId();
                            if (entidad != null  ) {
                                entidad.setFreezer_name(freezer_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!freezer_name.toString().isEmpty()) {
                                    entidad.setFreezer_name(freezer_name);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("rack_name")){
                            rack_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            rack_name = rackService.getRackByUserId(rack_name).getSystemId();
                            if (entidad != null  ) {
                                entidad.setRack_name(rack_name);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!rack_name.toString().isEmpty()) {
                                    entidad.setRack_name(rack_name);
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("box_name")){
                            box_name = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            box_name = boxService.getBoxByUserId(box_name).getSystemId();
                            if (entidad != null  ) {
                                entidad.setBox_name(box_name) ;
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!box_name.toString().isEmpty()) {
                                    entidad.setBox_name(box_name) ;
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("position")){
                            position = Integer.parseInt(sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());

                            if (entidad != null  ) {
                                entidad.setPosition_in_the_box((position));
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!position.toString().isEmpty()) {
                                    entidad.setPosition_in_the_box((position));
                                }
                            }
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("additional_comments")){
                            additional_comments = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();

                            if (entidad != null  ) {
                                entidad.setAdditional_comments(additional_comments);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                if(!additional_comments.toString().isEmpty()) {
                                    entidad.setAdditional_comments(additional_comments);
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
                    this.antibodyservice.saveAntibody(entidad);
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
            return "capture/antibodies/uploadResulExcelt";
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            modelMap.addAttribute("importError", true);
            modelMap.addAttribute("errorMessage", e.getMessage());
            modelMap.addAttribute("entidades", entidades);
            return "capture/antibodies/uploadResultExcel";
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

        return "capture/antibodies/uploadResultExcel";
    }
}
