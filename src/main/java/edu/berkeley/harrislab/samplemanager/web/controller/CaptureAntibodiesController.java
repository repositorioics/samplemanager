package edu.berkeley.harrislab.samplemanager.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.berkeley.harrislab.samplemanager.domain.*;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.*;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
}
