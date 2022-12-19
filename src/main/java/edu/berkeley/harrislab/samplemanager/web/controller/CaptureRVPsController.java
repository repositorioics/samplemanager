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
@RequestMapping("/capture/RVPs/*")
public class CaptureRVPsController {
    private static final Logger logger = LoggerFactory.getLogger(CaptureRVPsController.class);

    @Resource(name="RVPsService")
    private RVPsService RVPsService;

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

    @Resource(name="RVPsFilterService")
    private RVPsFilterService  RVPsFilterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException {
        logger.debug("Mostrando registros en JSP");


        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureRVPspage");
        return "capture/RVPs/list";
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
        return "capture/RVPs/enterNewForm";
    }

    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
    public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
        RVP entidadEditar = this.RVPsService.getRVPsBySystemId(systemId) ;
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
            List <RVP> specBox = this.RVPsService.getRVPsForBox(entidadEditar.getLoc_box().toString()) ;
            for(RVP spec:specBox) {
                String posicion = String.valueOf(spec.getLoc_pos());
                posiciones.remove(posicion);
            }

            model.addAttribute("posiciones",posiciones);

            model.addAttribute("entidad",entidadEditar);
            List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
            model.addAttribute("substudies",substudies);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditprimerspage");
            return "capture/RVPs/editForm";
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
            , @RequestParam( value="rvpId", required=true) String rvpId
            , @RequestParam( value="batchName", required=true) String batchName
            , @RequestParam( value="virusSerotype", required=false, defaultValue="") String virusSerotype
            , @RequestParam( value="provider", required=true) String provider
            , @RequestParam( value="date_exp", required=false, defaultValue="") String date_exp
            , @RequestParam( value="aliquot_volume", required=false, defaultValue="") String aliquot_volume
            , @RequestParam( value="num_aliq", required=false, defaultValue="") String num_aliq
            , @RequestParam( value="working_dilution", required=false, defaultValue="") String working_dilution
            , @RequestParam( value="infection_percentage", required=false, defaultValue="") String infection_percentage
            , @RequestParam( value="comments", required=false, defaultValue="") String comments
            , @RequestParam( value="cellType", required=false, defaultValue="") String cellType
            , @RequestParam( value="Time_hpi", required=false, defaultValue="") String Time_hpi
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

            RVP  entidad  = new RVP(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!systemId.equals("")) entidad.setSystemId(systemId);

            if (!rvpId.equals("")) entidad.setRvpId(rvpId);
            if (!batchName.equals("")) entidad.setBatchName( batchName);
            if (!virusSerotype.equals("")) entidad.setVirusSerotype( virusSerotype);
            if (!provider.equals("")) entidad.setProvider(provider);

            if (!date_exp.equals("")) {
                fechaIngreso=formatter.parse(date_exp);
                entidad.setDate_exp(fechaIngreso);
            }

            if (!aliquot_volume.equals("")) entidad.setAliquot_volume(Float.valueOf(aliquot_volume));
            if (!num_aliq.equals("")) entidad.setNum_aliq(Integer.valueOf((num_aliq)));
            if (!working_dilution.equals("")) entidad.setWorking_dilution(working_dilution);

            if (!infection_percentage.equals("")) entidad.setInfection_percentage((infection_percentage));

            if (!cellType.equals("")) entidad.setCellType(cellType);

            if (!Time_hpi.equals("")) entidad.setTime_hpi(Time_hpi);

            if (!comments.equals("")) entidad.setComments(comments);

            if (!loc_freezer.equals("")) entidad.setLoc_freezer(loc_freezer);

            if (!loc_rack.equals("")) entidad.setLoc_rack(loc_rack);

            if (!loc_box.equals("")) entidad.setLoc_box(loc_box);

            if (!loc_pos.equals(1) ) {
                if (!loc_pos.equals("")) entidad.setLoc_pos(loc_pos) ;
            }


            this.RVPsService.saveRVPs(entidad);

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
    private RVPsFilters jsonToFilter(String strJson) throws Exception {
        JsonObject jObjectFiltro = new Gson().fromJson(strJson, JsonObject.class);
        RVPsFilters antFilters = new RVPsFilters();
        String rvpId = null;
        Integer num_aliq = null;
        String virusSerotype = null;
        String batchName = null;


        Integer activeSearch = null;

        if (jObjectFiltro.get("rvpId") != null && !jObjectFiltro.get("rvpId").getAsString().isEmpty())
            rvpId = jObjectFiltro.get("rvpId").getAsString();
        if (jObjectFiltro.get("num_aliq") != null && !jObjectFiltro.get("num_aliq").getAsString().isEmpty())
            num_aliq = jObjectFiltro.get("num_aliq").getAsInt();
        if (jObjectFiltro.get("virusSerotype") != null && !jObjectFiltro.get("virusSerotype").getAsString().isEmpty())
            virusSerotype = jObjectFiltro.get("virusSerotype").getAsString();
        if (jObjectFiltro.get("batchName") != null && !jObjectFiltro.get("batchName").getAsString().isEmpty())
            batchName = jObjectFiltro.get("batchName").getAsString();


        if (jObjectFiltro.get("activeSearch") != null && !jObjectFiltro.get("activeSearch").getAsString().isEmpty())
            activeSearch = jObjectFiltro.get("activeSearch").getAsInt();


        antFilters.setRvpId(rvpId);
        antFilters.setNum_aliq(num_aliq);
        antFilters.setVirusSerotype(virusSerotype);
        antFilters.setBatchName(batchName);

        antFilters.setActiveSearch(activeSearch);

        return antFilters;
    }

    /**
     * Custom handler for searching.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/search/", method=RequestMethod.GET)
    public @ResponseBody List<RvpsResults> searchProcess(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
        RVPsFilters specFilters = jsonToFilter(filtro);
        List<RvpsResults> primersList;
        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");



        if(specFilters.getActiveSearch() == 0){
            primersList = new ArrayList<>();
        }else {

            primersList = RVPsFilterService.getRVPsByFilter(specFilters) ;
        }
        String lang = LocaleContextHolder.getLocale().getLanguage();
        for(RvpsResults antib:primersList) {
            String descCatalogo = null;

        }

        System.out.println("primersList = "+ primersList.toArray().toString());
        return primersList ;
    }

    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
    public ResponseEntity<String> processEntity(@RequestParam( value="rvpId", required=false ) String rvpId

            , @RequestParam( value="batchName", required=true) String batchName
            , @RequestParam( value="virusSerotype", required=false, defaultValue="") String virusSerotype
            , @RequestParam( value="provider", required=true) String provider
            , @RequestParam( value="date_exp", required=false, defaultValue="") String date_exp
            , @RequestParam( value="aliquot_volume", required=false, defaultValue="") String aliquot_volume
            , @RequestParam( value="num_aliq", required=false, defaultValue="") String num_aliq
            , @RequestParam( value="working_dilution", required=false, defaultValue="") String working_dilution
            , @RequestParam( value="infection_percentage", required=false, defaultValue="") String infection_percentage
            , @RequestParam( value="comments", required=false, defaultValue="") String comments
            , @RequestParam( value="cellType", required=false, defaultValue="") String cellType
            , @RequestParam( value="Time_hpi", required=false, defaultValue="") String Time_hpi
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

            RVP  entidad  = new RVP(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');

            if (!rvpId.equals("")) entidad.setRvpId(rvpId);
            if (!batchName.equals("")) entidad.setBatchName( batchName);
            if (!virusSerotype.equals("")) entidad.setVirusSerotype( virusSerotype);
            if (!provider.equals("")) entidad.setProvider(provider);

            if (!date_exp.equals("")) {
                fechaIngreso=formatter.parse(date_exp);
                entidad.setDate_exp(fechaIngreso);
            }

            if (!aliquot_volume.equals("")) entidad.setAliquot_volume(Float.valueOf(aliquot_volume));
            if (!num_aliq.equals("")) entidad.setNum_aliq(Integer.valueOf((num_aliq)));
            if (!working_dilution.equals("")) entidad.setWorking_dilution(working_dilution);

            if (!infection_percentage.equals("")) entidad.setInfection_percentage((infection_percentage));

            if (!cellType.equals("")) entidad.setCellType(cellType);

            if (!Time_hpi.equals("")) entidad.setTime_hpi(Time_hpi);

            if (!comments.equals("")) entidad.setComments(comments);

            if (!loc_freezer.equals("")) entidad.setLoc_freezer(loc_freezer);

            if (!loc_rack.equals("")) entidad.setLoc_rack(loc_rack);

            if (!loc_box.equals("")) entidad.setLoc_box(loc_box);

            if (!loc_pos.equals(1) ) {
                if (!loc_pos.equals("")) entidad.setLoc_pos(loc_pos) ;
            }


            this.RVPsService.saveRVPs(entidad);

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
