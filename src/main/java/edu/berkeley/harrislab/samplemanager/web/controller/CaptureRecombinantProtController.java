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
}
