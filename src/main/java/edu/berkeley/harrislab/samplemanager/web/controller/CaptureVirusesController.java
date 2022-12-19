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
@RequestMapping("/capture/viruses/*")
public class CaptureVirusesController {
    private static final Logger logger = LoggerFactory.getLogger(CaptureVirusesController.class);

    @Resource(name="VirusesService")
    private VirusesService VirusesService;

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

    @Resource(name="VirusesFilterService")
    private VirusesFilterService  VirusesFilterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException {
        logger.debug("Mostrando registros en JSP");


        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureVirusespage");
        return "capture/viruses/list";
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

        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureVirusespage");
        return "capture/viruses/enterNewForm";
    }

    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
    public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
        Viruses entidadEditar = this.VirusesService.getVirusesBySystemId(systemId) ;
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
            List <Viruses> specBox = this.VirusesService.getVirusesForBox(entidadEditar.getLoc_box().toString()) ;
            for(Viruses spec:specBox) {
                String posicion = String.valueOf(spec.getLoc_pos());
                posiciones.remove(posicion);
            }

            model.addAttribute("posiciones",posiciones);

            model.addAttribute("entidad",entidadEditar);
            List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
            model.addAttribute("substudies",substudies);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditvirusespage");
            return "capture/viruses/editForm";
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
            , @RequestParam( value="virusId", required=true) String virusId
            , @RequestParam( value="virus_Serotype", required=true) String virus_Serotype
            , @RequestParam( value="strain", required=false, defaultValue="") String strain
            , @RequestParam( value="batch_number", required=true) String batch_number
            , @RequestParam( value="passage", required=false, defaultValue="") String passage
            , @RequestParam( value="experiment_id", required=false, defaultValue="") String experiment_id
            , @RequestParam( value="stage_of_production", required=false, defaultValue="") String stage_of_production
            , @RequestParam( value="amount_of_Unconc_virus", required=false, defaultValue="") String amount_of_Unconc_virus
            , @RequestParam( value="date_Uncon_Collected", required=false, defaultValue="") String date_Uncon_Collected
            , @RequestParam( value="aliquot_volume", required=false, defaultValue="") String aliquot_volume
            , @RequestParam( value="amount_of_Conc_virus", required=false, defaultValue="") String amount_of_Conc_virus
            , @RequestParam( value="date_of_Conc", required=false, defaultValue="") String date_of_Conc
            , @RequestParam( value="amount_of_Purified", required=false, defaultValue="") String amount_of_Purified
            , @RequestParam( value="date_of_Purification", required=false, defaultValue="") String date_of_Purification
            , @RequestParam( value="qc_PCR_Check", required=false, defaultValue="") String qc_PCR_Check
            , @RequestParam( value="qc_ELISA_Check", required=false, defaultValue="") String qc_ELISA_Check
            , @RequestParam( value="notes_on_QC", required=false, defaultValue="") String notes_on_QC
            , @RequestParam( value="num_of_collections", required=false, defaultValue="") String num_of_collections
            , @RequestParam( value="day_Virus_Collected", required=false, defaultValue="") String day_Virus_Collected
            , @RequestParam( value="cell_line_and_passage", required=false, defaultValue="") String cell_line_and_passage
            , @RequestParam( value="viral_inoculum_vial_ID", required=false, defaultValue="") String viral_inoculum_vial_ID
            , @RequestParam( value="bca_Concentration", required=false, defaultValue="") String bca_Concentration
            , @RequestParam( value="fluorospot_check", required=false, defaultValue="") String fluorospot_check
            , @RequestParam( value="notes_on_FS_check", required=false, defaultValue="") String notes_on_FS_check
            , @RequestParam( value="viral_Titer", required=false, defaultValue="") String viral_Titer
            , @RequestParam( value="storage_temperature", required=false, defaultValue="") String storage_temperature
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

            Viruses  entidad  = new Viruses(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!systemId.equals("")) entidad.setSystemId(systemId);

            if (!virusId.equals("")) entidad.setVirusId(virusId);
            if (!virus_Serotype.equals("")) entidad.setVirus_Serotype( virus_Serotype);
            if (!strain.equals("")) entidad.setStrain( strain);
            if (!batch_number.equals("")) entidad.setBatch_number(batch_number);

            if (!date_Uncon_Collected.equals("")) {
                fechaIngreso=formatter.parse(date_Uncon_Collected);
                entidad.setDate_Uncon_Collected(fechaIngreso);
            }

            if (!aliquot_volume.equals("")) entidad.setAliquot_volume(Float.valueOf(aliquot_volume));
            if (!passage.equals("")) entidad.setPassage(passage);
            if (!experiment_id.equals("")) entidad.setExperiment_id(experiment_id);

            if (!stage_of_production.equals("")) entidad.setStage_of_production((stage_of_production));

            if (!amount_of_Unconc_virus.equals("")) entidad.setAmount_of_Unconc_virus(amount_of_Unconc_virus);

            if (!amount_of_Conc_virus.equals("")) entidad.setAmount_of_Conc_virus(amount_of_Conc_virus);

            if (!date_of_Conc.equals("")) {
                fechaIngreso=formatter.parse(date_of_Conc);
                entidad.setDate_of_Conc(fechaIngreso);
            }

            if (!amount_of_Purified.equals("")) entidad.setAmount_of_Purified(amount_of_Purified);

            if (!date_of_Purification.equals("")) {
                fechaIngreso=formatter.parse(date_of_Purification);
                entidad.setDate_of_Purification(fechaIngreso);
            }

            if (!qc_PCR_Check.equals("")) entidad.setQc_PCR_Check(qc_PCR_Check);
            if (!qc_ELISA_Check.equals("")) entidad.setQc_ELISA_Check(qc_ELISA_Check);
            if (!notes_on_QC.equals("")) entidad.setNotes_on_QC(notes_on_QC);
            if (!num_of_collections.equals("")) entidad.setNum_of_collections(num_of_collections);
            if (!day_Virus_Collected.equals("")) entidad.setDay_Virus_Collected(day_Virus_Collected);
            if (!cell_line_and_passage.equals("")) entidad.setCell_line_and_passage(cell_line_and_passage);
            if (!viral_inoculum_vial_ID.equals("")) entidad.setViral_inoculum_vial_ID(viral_inoculum_vial_ID);
            if (!bca_Concentration.equals("")) entidad.setBca_Concentration(bca_Concentration);
            if (!fluorospot_check.equals("")) entidad.setFluorospot_check(fluorospot_check);

            if (!notes_on_FS_check.equals("")) entidad.setNotes_on_FS_check(notes_on_FS_check);
            if (!viral_Titer.equals("")) entidad.setViral_Titer(viral_Titer);
            if (!storage_temperature.equals("")) entidad.setStorage_temperature(storage_temperature);

            if (!comments.equals("")) entidad.setComments(comments);

            if (!loc_freezer.equals("")) entidad.setLoc_freezer(loc_freezer);

            if (!loc_rack.equals("")) entidad.setLoc_rack(loc_rack);

            if (!loc_box.equals("")) entidad.setLoc_box(loc_box);

            if (!loc_pos.equals(1) ) {
                if (!loc_pos.equals("")) entidad.setLoc_pos(loc_pos) ;
            }


            this.VirusesService.saveViruses(entidad);

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
    private VirusessFilters jsonToFilter(String strJson) throws Exception {
        JsonObject jObjectFiltro = new Gson().fromJson(strJson, JsonObject.class);
        VirusessFilters antFilters = new VirusessFilters();
        String virusId = null;
        Integer num_aliquots = null;
        String experiment_id = null;
        String virus_Serotype = null;
        Float aliquot_volume = null;


        Integer activeSearch = null;

        if (jObjectFiltro.get("virusId") != null && !jObjectFiltro.get("virusId").getAsString().isEmpty())
            virusId = jObjectFiltro.get("virusId").getAsString();
        if (jObjectFiltro.get("num_aliquots") != null && !jObjectFiltro.get("num_aliquots").getAsString().isEmpty())
            num_aliquots = jObjectFiltro.get("num_aliquots").getAsInt();
        if (jObjectFiltro.get("experiment_id") != null && !jObjectFiltro.get("experiment_id").getAsString().isEmpty())
            experiment_id = jObjectFiltro.get("experiment_id").getAsString();
        if (jObjectFiltro.get("aliquot_volume") != null && !jObjectFiltro.get("aliquot_volume").getAsString().isEmpty())
            aliquot_volume = jObjectFiltro.get("aliquot_volume").getAsFloat();


        if (jObjectFiltro.get("activeSearch") != null && !jObjectFiltro.get("activeSearch").getAsString().isEmpty())
            activeSearch = jObjectFiltro.get("activeSearch").getAsInt();


        antFilters.setVirusId(virusId);
        antFilters.setNum_aliquots(num_aliquots);
        antFilters.setExperiment_id(experiment_id);
        antFilters.setAliquot_volume(aliquot_volume);

        antFilters.setActiveSearch(activeSearch);

        return antFilters;
    }

    /**
     * Custom handler for searching.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/search/", method=RequestMethod.GET)
    public @ResponseBody List<VirusesResults> searchProcess(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
        VirusessFilters specFilters = jsonToFilter(filtro);
        List<VirusesResults> primersList;
        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");



        if(specFilters.getActiveSearch() == 0){
            primersList = new ArrayList<>();
        }else {

            primersList = VirusesFilterService.getVirusesByFilter(specFilters) ;
        }
        String lang = LocaleContextHolder.getLocale().getLanguage();
        for(VirusesResults antib:primersList) {
            String descCatalogo = null;

        }

        System.out.println("virusesList = "+ primersList.toArray().toString());
        return primersList ;
    }

    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
    public ResponseEntity<String> processEntity(@RequestParam( value="virusId", required=false ) String virusId
            , @RequestParam( value="virus_Serotype", required=true) String virus_Serotype
            , @RequestParam( value="strain", required=false, defaultValue="") String strain
            , @RequestParam( value="batch_number", required=true) String batch_number
            , @RequestParam( value="passage", required=false, defaultValue="") String passage
            , @RequestParam( value="experiment_id", required=false, defaultValue="") String experiment_id
            , @RequestParam( value="stage_of_production", required=false, defaultValue="") String stage_of_production
            , @RequestParam( value="amount_of_Unconc_virus", required=false, defaultValue="") String amount_of_Unconc_virus
            , @RequestParam( value="date_Uncon_Collected", required=false, defaultValue="") String date_Uncon_Collected
            , @RequestParam( value="aliquot_volume", required=false, defaultValue="") String aliquot_volume
            , @RequestParam( value="amount_of_Conc_virus", required=false, defaultValue="") String amount_of_Conc_virus
            , @RequestParam( value="date_of_Conc", required=false, defaultValue="") String date_of_Conc
            , @RequestParam( value="amount_of_Purified", required=false, defaultValue="") String amount_of_Purified
            , @RequestParam( value="date_of_Purification", required=false, defaultValue="") String date_of_Purification
            , @RequestParam( value="qc_PCR_Check", required=false, defaultValue="") String qc_PCR_Check
            , @RequestParam( value="qc_ELISA_Check", required=false, defaultValue="") String qc_ELISA_Check
            , @RequestParam( value="notes_on_QC", required=false, defaultValue="") String notes_on_QC
            , @RequestParam( value="num_of_collections", required=false, defaultValue="") String num_of_collections
            , @RequestParam( value="day_Virus_Collected", required=false, defaultValue="") String day_Virus_Collected
            , @RequestParam( value="cell_line_and_passage", required=false, defaultValue="") String cell_line_and_passage
            , @RequestParam( value="viral_inoculum_vial_ID", required=false, defaultValue="") String viral_inoculum_vial_ID
            , @RequestParam( value="bca_Concentration", required=false, defaultValue="") String bca_Concentration
            , @RequestParam( value="fluorospot_check", required=false, defaultValue="") String fluorospot_check
            , @RequestParam( value="notes_on_FS_check", required=false, defaultValue="") String notes_on_FS_check
            , @RequestParam( value="viral_Titer", required=false, defaultValue="") String viral_Titer
            , @RequestParam( value="storage_temperature", required=false, defaultValue="") String storage_temperature
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

            Viruses  entidad  = new Viruses(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!virusId.equals("")) entidad.setVirusId(virusId);
            if (!virus_Serotype.equals("")) entidad.setVirus_Serotype( virus_Serotype);
            if (!strain.equals("")) entidad.setStrain( strain);
            if (!batch_number.equals("")) entidad.setBatch_number(batch_number);

            if (!date_Uncon_Collected.equals("")) {
                fechaIngreso=formatter.parse(date_Uncon_Collected);
                entidad.setDate_Uncon_Collected(fechaIngreso);
            }

            if (!aliquot_volume.equals("")) entidad.setAliquot_volume(Float.valueOf(aliquot_volume));
            if (!passage.equals("")) entidad.setPassage(passage);
            if (!experiment_id.equals("")) entidad.setExperiment_id(experiment_id);

            if (!stage_of_production.equals("")) entidad.setStage_of_production((stage_of_production));

            if (!amount_of_Unconc_virus.equals("")) entidad.setAmount_of_Unconc_virus(amount_of_Unconc_virus);

            if (!amount_of_Conc_virus.equals("")) entidad.setAmount_of_Conc_virus(amount_of_Conc_virus);

            if (!date_of_Conc.equals("")) {
                fechaIngreso=formatter.parse(date_of_Conc);
                entidad.setDate_of_Conc(fechaIngreso);
            }

            if (!amount_of_Purified.equals("")) entidad.setAmount_of_Purified(amount_of_Purified);

            if (!date_of_Purification.equals("")) {
                fechaIngreso=formatter.parse(date_of_Purification);
                entidad.setDate_of_Purification(fechaIngreso);
            }

            if (!qc_PCR_Check.equals("")) entidad.setQc_PCR_Check(qc_PCR_Check);
            if (!qc_ELISA_Check.equals("")) entidad.setQc_ELISA_Check(qc_ELISA_Check);
            if (!notes_on_QC.equals("")) entidad.setNotes_on_QC(notes_on_QC);
            if (!num_of_collections.equals("")) entidad.setNum_of_collections(num_of_collections);
            if (!day_Virus_Collected.equals("")) entidad.setDay_Virus_Collected(day_Virus_Collected);
            if (!cell_line_and_passage.equals("")) entidad.setCell_line_and_passage(cell_line_and_passage);
            if (!viral_inoculum_vial_ID.equals("")) entidad.setViral_inoculum_vial_ID(viral_inoculum_vial_ID);
            if (!bca_Concentration.equals("")) entidad.setBca_Concentration(bca_Concentration);
            if (!fluorospot_check.equals("")) entidad.setFluorospot_check(fluorospot_check);

            if (!notes_on_FS_check.equals("")) entidad.setNotes_on_FS_check(notes_on_FS_check);
            if (!viral_Titer.equals("")) entidad.setViral_Titer(viral_Titer);
            if (!storage_temperature.equals("")) entidad.setStorage_temperature(storage_temperature);

            if (!comments.equals("")) entidad.setComments(comments);

            if (!loc_freezer.equals("")) entidad.setLoc_freezer(loc_freezer);

            if (!loc_rack.equals("")) entidad.setLoc_rack(loc_rack);

            if (!loc_box.equals("")) entidad.setLoc_box(loc_box);

            if (!loc_pos.equals(1) ) {
                if (!loc_pos.equals("")) entidad.setLoc_pos(loc_pos) ;
            }


            this.VirusesService.saveViruses(entidad);

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
