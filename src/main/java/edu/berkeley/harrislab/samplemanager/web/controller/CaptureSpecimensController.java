package edu.berkeley.harrislab.samplemanager.web.controller;

import com.google.gson.JsonObject;
import edu.berkeley.harrislab.samplemanager.domain.*;
import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.*;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import javax.annotation.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/capture/specimens/*")
public class CaptureSpecimensController {
	private static final Logger logger = LoggerFactory.getLogger(CaptureSpecimensController.class);
	@Resource(name="specimenService")
	private SpecimenService specimenService;
	@Resource(name="specimenStorageService")
	private SpecimenStorageService specimenStorageService;
	@Resource(name="studyService")
	private StudyService studyService;
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="equipService")
	private EquipService equipService;
	@Resource(name="rackService")
	private RackService rackService;
	@Resource(name="boxService")
	private BoxService boxService;
	@Resource(name="subjectService")
	private SubjectService subjectService;
	
	@Resource(name="visitsService")
	private VisitsService visitsService;

	@Resource(name="specimenFilterService")
	private SpecimenFilterService specimenFilterService;

    @Resource(name="DownSQLUpdateService")
    private DownSQLUpdateService DownSQLUpdateService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException { 	
    	logger.debug("Mostrando registros en JSP");

    //	List<SpecimensResults> entidades = specimenFilterService.getSpecimensByFilter();
    /*	for(SpecimensResults specimen:entidades) {
    		MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(specimen.getSpecimenType(),"CAT_SP_TYPE");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setSpecimenType(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(specimen.getInStorage(),"CAT_SINO");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setInStorage(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(specimen.getVolUnits(),"CAT_VOL_UNITS");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setVolUnits(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(specimen.getSpecimenCondition(),"CAT_SP_COND");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setSpecimenCondition(descCatalogo);
    		}
    	}*/


		List<Subject> subjects = this.subjectService.getActiveSubjects();
		model.addAttribute("subjects",subjects);
    //	model.addAttribute("entidades", entidades);
    	this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturespecimenspage");
    	return "capture/specimens/list";
	}
	
	
	/**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String initAddEntityForm(Model model) {
    	List<MessageResource> types = this.messageResourceService.getCatalogo("CAT_SP_TYPE");
	    model.addAttribute("types",types);
	    List<MessageResource> sinos = this.messageResourceService.getCatalogo("CAT_SINO");
	    model.addAttribute("sinos",sinos);
	    List<MessageResource> volUnits = this.messageResourceService.getCatalogo("CAT_VOL_UNITS");
	    model.addAttribute("volUnits",volUnits);
	    List<MessageResource> conditions = this.messageResourceService.getCatalogo("CAT_SP_COND");
	    model.addAttribute("conditions",conditions);
	    List<Subject> subjects = this.subjectService.getActiveSubjects();
	    model.addAttribute("subjects",subjects);
	    List<Equip> equips = this.equipService.getActiveEquips();
	    model.addAttribute("equips",equips);
		List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
		model.addAttribute("substudies",substudies);
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturenewspecimenpage");
		return "capture/specimens/enterNewForm";
	}
    
    
    
    /**
     * Custom handler for displaying a entidad.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{systemId}/")
    public ModelAndView showEntity(@PathVariable("systemId") String systemId) {
    	ModelAndView mav;
    	Specimen entidad = this.specimenService.getSpecimenBySystemId(systemId);
        if(entidad==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("capture/specimens/viewForm");
        	MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(entidad.getSpecimenType(),"CAT_SP_TYPE");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setSpecimenType(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(entidad.getInStorage(),"CAT_SINO");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setInStorage(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(entidad.getVolUnits(),"CAT_VOL_UNITS");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setVolUnits(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(entidad.getSpecimenCondition(),"CAT_SP_COND");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			entidad.setSpecimenCondition(descCatalogo);
    		}
            List<AuditTrail> bitacoraEntidad = auditTrailService.getBitacora(systemId);
            
            SpecimenStorage entidad2 = this.specimenStorageService.getSpecimenStorageBySpecId(systemId);
            List<AuditTrail> bitacoraEntidad2 = null;
            if (entidad2!=null) {
            	bitacoraEntidad2 = auditTrailService.getBitacora(entidad2.getStorageId());
            	for(AuditTrail b:bitacoraEntidad2) {
                	bitacoraEntidad.add(b);
                }
            }
            mav.addObject("entidad",entidad);
            mav.addObject("entidad2",entidad2);
            mav.addObject("bitacora",bitacoraEntidad);
            this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureviewspecimenpage");
        }
        return mav;
    }
    
    
	/**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{systemId}/", method = RequestMethod.GET)
	public String initEnterForm(@PathVariable("systemId") String systemId, Model model) {
		Specimen entidadEditar = this.specimenService.getSpecimenBySystemId(systemId);
		if(entidadEditar!=null){
			List<MessageResource> types = this.messageResourceService.getCatalogo("CAT_SP_TYPE");
		    model.addAttribute("types",types);
		    List<MessageResource> sinos = this.messageResourceService.getCatalogo("CAT_SINO");
		    model.addAttribute("sinos",sinos);
		    List<MessageResource> volUnits = this.messageResourceService.getCatalogo("CAT_VOL_UNITS");
		    model.addAttribute("volUnits",volUnits);
		    List<MessageResource> conditions = this.messageResourceService.getCatalogo("CAT_SP_COND");
		    model.addAttribute("conditions",conditions);
		    List<Subject> subjects = this.subjectService.getActiveSubjects();
		    model.addAttribute("subjects",subjects);
		    List<Box> boxes = this.boxService.getActiveBoxes();
		    model.addAttribute("boxes",boxes);
			model.addAttribute("entidad",entidadEditar);
			List<MessageResource> substudies = this.messageResourceService.getCatalogo("CAT_SUBSTUDIES");
			model.addAttribute("substudies",substudies);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditspecimenpage");
			return "capture/specimens/editForm";
		}
		else{
			return "403";
		}
	}
    
    
	/**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param the ID
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editStorageEntity/{systemId}/", method = RequestMethod.GET)
	public String initEditStorageForm(@PathVariable("systemId") String systemId, Model model) {
		SpecimenStorage entidadEditar = this.specimenStorageService.getSpecimenStorageBySystemId(systemId);
		if(entidadEditar!=null){
			
			List<Equip> equips = this.equipService.getActiveEquips();
		    model.addAttribute("equips",equips);
			model.addAttribute("entidad",entidadEditar);
		    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureeditspecimenpage");
			return "capture/specimens/editStorageForm";
		}
		else{
			return "403";
		}
	}
    
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/deleteStorageEntity/{systemId}/")
    public String disableStorageEntity(@PathVariable("systemId") String systemId, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	SpecimenStorage entidadEliminar = this.specimenStorageService.getSpecimenStorageBySystemId(systemId);
    	if(entidadEliminar!=null){
    		Specimen spec = entidadEliminar.getSpecimen();
    		this.specimenStorageService.deleteSpecimenStorage(entidadEliminar);
    		spec.setInStorage("0");
    		this.specimenService.saveSpecimen(spec);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", spec.getSpecimenId());
    		redirecTo = "redirect:/capture/specimens/"+spec.getSystemId()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processNewEntity(@RequestParam( value="specimenId", required=true ) String specimenId
	        , @RequestParam( value="specimenType", required=true) String specimenType
	        , @RequestParam( value="specimenCondition", required=false, defaultValue="") String specimenCondition
	        , @RequestParam( value="labReceiptDate", required=true) String labReceiptDate
	        , @RequestParam( value="volume", required=false, defaultValue="") String volume
	        , @RequestParam( value="varA", required=false, defaultValue="") String varA
	        , @RequestParam( value="varB", required=false, defaultValue="") String varB
	        , @RequestParam( value="volUnits", required=false, defaultValue="") String volUnits
	        , @RequestParam( value="obs", required=false, defaultValue="") String obs
	        , @RequestParam( value="subjectSpecId", required=false, defaultValue="") String subjectSpecId
	        , @RequestParam( value="inStorage", required=true) String inStorage
	        , @RequestParam( value="storageDate", required=false, defaultValue="") String storageDate
	        , @RequestParam( value="orthocode", required=false, defaultValue="") String orthocode
	        , @RequestParam( value="boxSpecId", required=false, defaultValue="") String boxSpecId
	        , @RequestParam( value="position", required=false, defaultValue="") String position
			, @RequestParam( value="substudy", required=false) String substudy
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaIngreso =  null;
    		Date fechaAlmacenamiento =  null;
    		WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    		Specimen entidad  = new Specimen(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
    		SpecimenStorage entidad2 = null;
			if (!specimenId.equals("")) entidad.setSpecimenId(specimenId);
			if (!specimenType.equals("")) entidad.setSpecimenType(specimenType);
			if (!specimenCondition.equals("")) entidad.setSpecimenCondition(specimenCondition);
			if (!labReceiptDate.equals("")) {
				fechaIngreso=formatter.parse(labReceiptDate);
				entidad.setLabReceiptDate(fechaIngreso);
			}
			if (!substudy.equals("")) entidad.setSubstudy(substudy);
			if (!inStorage.equals("")) entidad.setInStorage(inStorage);
			if (!volume.equals("")) entidad.setVolume(Float.valueOf(volume));
			if (!varA.equals("")) entidad.setVarA(Integer.valueOf(varA));
			if (!varB.equals("")) entidad.setVarB(Integer.valueOf(varB));
			if (!volUnits.equals("")) entidad.setVolUnits(volUnits);
			if (!obs.equals("")) entidad.setObs(obs);
			if (!orthocode.equals("")) entidad.setOrthocode(orthocode);
			if (!subjectSpecId.equals("")) entidad.setSubjectId(this.subjectService.getSubjectBySystemId(subjectSpecId));

			if (entidad.getInStorage().equals("1")) {
				entidad2 = new SpecimenStorage(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
				entidad2.setSpecimen(entidad);
				if (!storageDate.equals("")) {
					fechaAlmacenamiento=formatter.parse(storageDate);
					entidad2.setStorageDate(fechaAlmacenamiento);
				}
				if (!boxSpecId.equals("")) entidad2.setBox(this.boxService.getBoxBySystemId(boxSpecId));
				if (!position.equals("")) entidad2.setPos(Integer.valueOf(position));
				this.specimenStorageService.saveSpecimenStorage(entidad2);
			}else {
				this.specimenService.saveSpecimen(entidad);
			}
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
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEditEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processEditEntity( @RequestParam(value="systemId", required=true) String systemId
	        , @RequestParam( value="specimenId", required=true ) String specimenId
	        , @RequestParam( value="specimenType", required=true) String specimenType
	        , @RequestParam( value="specimenCondition", required=false, defaultValue="") String specimenCondition
	        , @RequestParam( value="labReceiptDate", required=true) String labReceiptDate
	        , @RequestParam( value="varA", required=false, defaultValue="") String varA
	        , @RequestParam( value="varB", required=false, defaultValue="") String varB
	        , @RequestParam( value="volume", required=false, defaultValue="") String volume
	        , @RequestParam( value="volUnits", required=false, defaultValue="") String volUnits
	        , @RequestParam( value="obs", required=false, defaultValue="") String obs
	        , @RequestParam( value="orthocode", required=false, defaultValue="") String orthocode
	        , @RequestParam( value="subjectSpecId", required=false, defaultValue="") String subjectSpecId
			, @RequestParam( value="substudy", required=false) String substudy
            , @RequestParam( value="estado", required=false) String estado
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaIngreso =  null;
    		Specimen entidad = this.specimenService.getSpecimenBySystemId(systemId);
			if (!systemId.equals("")) entidad.setSystemId(systemId);
			if (!specimenId.equals("")) entidad.setSpecimenId(specimenId);
			if (!specimenType.equals("")) entidad.setSpecimenType(specimenType);
			if (!specimenCondition.equals("")) entidad.setSpecimenCondition(specimenCondition);
            if (!estado.equals("")) entidad.setEstado(estado);
			if (!labReceiptDate.equals("")) {
				fechaIngreso=formatter.parse(labReceiptDate);
				entidad.setLabReceiptDate(fechaIngreso);

			}
			if (!substudy.equals("")) entidad.setSubstudy(substudy);
			if (!volume.equals("")) {
				entidad.setVolume(Float.valueOf(volume));
			}else {
				entidad.setVolume(null);
			}
			if (!varA.equals("")) {
				entidad.setVarA(Integer.valueOf(varA));
			}else {
				entidad.setVarA(null);
			}
			if (!varB.equals("")) {
				entidad.setVarB(Integer.valueOf(varB));
			}else {
				entidad.setVarB(null);
			}
			if (!volUnits.equals("")) {
				entidad.setVolUnits(volUnits);
			}else {
				entidad.setVolUnits(null);
			}
			entidad.setObs(obs);
			entidad.setOrthocode(orthocode);
			if (!subjectSpecId.equals("")) {
				entidad.setSubjectId(this.subjectService.getSubjectBySystemId(subjectSpecId));
			}else {
				entidad.setSubjectId(null);
			}
			this.specimenService.saveSpecimen(entidad);
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
     * Custom handler for saving.
     * 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveStorageEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processStorageEntity(@RequestParam(value="storageId", required=true) String storageId
			, @RequestParam( value="storageDate", required=false, defaultValue="") String storageDate
	        , @RequestParam( value="boxSpecId", required=false, defaultValue="") String boxSpecId
	        , @RequestParam( value="position", required=false, defaultValue="") String position
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaAlmacenamiento =  null;
    		SpecimenStorage entidad = this.specimenStorageService.getSpecimenStorageBySystemId(storageId);
    		if (!storageDate.equals("")) {
				fechaAlmacenamiento=formatter.parse(storageDate);
				entidad.setStorageDate(fechaAlmacenamiento);
			}
			if (!boxSpecId.equals("")) entidad.setBox(this.boxService.getBoxBySystemId(boxSpecId));
			if (!position.equals("")) entidad.setPos(Integer.valueOf(position));
			this.specimenStorageService.updateSpecimenStorage(entidad);
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
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/disableEntity/{ident}/")
    public String disableEntity(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Specimen entidad = this.specimenService.getSpecimenBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('1');
    		this.specimenService.saveSpecimen(entidad);
    		redirectAttributes.addFlashAttribute("disabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getSpecimenId());
    		redirecTo = "redirect:/capture/specimens/"+entidad.getSystemId()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for enabling.
     *
     * @param ident the ID to enable
     * @param redirectAttributes
     * @return a String
     */
    @RequestMapping("/enableEntity/{ident}/")
    public String enableEntity(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Specimen entidad = this.specimenService.getSpecimenBySystemId(ident);
    	if(entidad!=null){
    		entidad.setPasive('0');
    		this.specimenService.saveSpecimen(entidad);
    		redirectAttributes.addFlashAttribute("enabledEntity", true);
    		redirectAttributes.addFlashAttribute("entityName", entidad.getSpecimenId());
    		redirecTo = "redirect:/capture/specimens/"+entidad.getSystemId()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    private ResponseEntity<String> createJsonResponse( Object o )
	{
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    Gson gson = new Gson();
	    String json = gson.toJson(o);
	    return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
	}
    
    
    /**
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/uploadEntity/", method = RequestMethod.GET)
	public String initUploadForm(Model model) {
	    this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureuploadspecimenpage");
		return "capture/specimens/uploadForm";
	}
    
    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
	public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
    	boolean checkLabReceiptDate = false;
    	String specimenId;
    	String specimenType,specimenTypeCatKey,inStorage, volUnits, record_user;

        volUnits = "";
        record_user = "";
        specimenType = "";
        inStorage = "";

        Float volume;
        Date record_date=null;
    	int nuevos =0, viejos=0;
    	Specimen entidad = new Specimen();
    	List<Specimen> entidades = new ArrayList<Specimen>();
    	WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    	UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

		try {
			//Read the file
			Reader in = new InputStreamReader(file.getInputStream());
			
			//Define the format
			CSVFormat format = CSVFormat.DEFAULT
				      .withFirstRecordAsHeader()
				      .withIgnoreHeaderCase();
			
			//Create the parser
			CSVParser parsed = CSVParser.parse(in, format);
			
			//Verify that labId Exist in the file
			List<String> encabezados = parsed.getHeaderNames();
			for(String encabezado:encabezados) {
				if(encabezado.equalsIgnoreCase("specimenId")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("specimenType")) {
					logger.info(encabezado + " found....");
				}
				else if(encabezado.equalsIgnoreCase("labReceiptDate")) {
					logger.info(encabezado + " found....");
					checkLabReceiptDate = true;
				}
                else if(encabezado.equalsIgnoreCase("Volume")) {
                    logger.info(encabezado + " found....");
                }
                else if(encabezado.equalsIgnoreCase("Volume_Units")) {
                    logger.info(encabezado + " found....");
                }
                else if(encabezado.equalsIgnoreCase("Record_date")) {
                    logger.info(encabezado + " found....");
                }
                else if(encabezado.equalsIgnoreCase("Record_User")) {
                    logger.info(encabezado + " found....");
                }
				else if(encabezado.equalsIgnoreCase("inStorage")) {
					logger.info(encabezado + " found....");
				}
			}
			
			//Create the records
			Iterable<CSVRecord> records = parsed.getRecords();

			//Iterate over the records record.get("specimenId")
		    for (CSVRecord record : records) {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		    	specimenId = record.get("specimenId").toString();

                if (!record.get("inStorage").equals("")){
                    inStorage = (record.get("inStorage"));
                }
                if (!record.get("specimenType").equals("")){
                    specimenType = (record.get("specimenType"));
                }
                if (!record.get("Volume").equals("")){
                    volume = Float.parseFloat(record.get("Volume"));
                    entidad.setVolume(volume);
                }
                if (!record.get("Volume_Units").equals("")){
                    volUnits = (record.get("Volume_Units"));
                    entidad.setVolUnits(volUnits);
                }
                if (!record.get("Record_date").equals("")){
                    record_date = formatter1.parse(record.get("Record_date"));
                    entidad.setRecordDate(record_date);
                }
                if (!record.get("Record_User").equals("")){
                    record_user = record.get("Record_User");
                    entidad.setRecordUser(record_user);
                }


		        entidad = this.specimenService.getSpecimenByUserId(specimenId);
		        if(entidad==null) {
		        	entidad = new Specimen(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
		        	nuevos++;
                    specimenType = record.get("specimenType");
                    inStorage = record.get("inStorage");
                    volume = Float.parseFloat(record.get("Volume"));
                    volUnits = record.get("Volume_Units");
                    if (!record.get("Record_date").equals("")){
                        record_date = formatter1.parse(record.get("Record_date"));
                    }
                    if (!record.get("Record_User").equals("")){
                        record_user = record.get("Record_User");
                    }

		        }
		        else {
		        	viejos++;


                }
			    entidad.setSpecimenId(specimenId);

			    specimenTypeCatKey = this.messageResourceService.getMensajeDesc(specimenType,"CAT_SP_TYPE").getCatKey();
			    entidad.setSpecimenType(specimenTypeCatKey);
			    if (checkLabReceiptDate) {
			    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			    	Date fechaIngreso=null;
			    	if (!record.get("labReceiptDate").equals(""))  fechaIngreso=formatter.parse(record.get("labReceiptDate"));

                    if (!record.get("labReceiptDate").equals(""))
					entidad.setLabReceiptDate(fechaIngreso);
				}
                if (!record.get("inStorage").equals(""))
                    entidad.setInStorage(inStorage);
			    this.specimenService.saveSpecimen(entidad);
			    entidades.add(entidad);
		    }
		}
		catch(IllegalArgumentException ile) {
			logger.error(ile.getLocalizedMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
			return "capture/specimens/uploadResult";
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			modelMap.addAttribute("importError", true);
			modelMap.addAttribute("errorMessage", e.getMessage());
			return "capture/specimens/uploadResult";
		}
		modelMap.addAttribute("entidades", entidades);
		modelMap.addAttribute("nuevos", nuevos);
		modelMap.addAttribute("viejos", viejos);
		for(Specimen specimen:entidades) {
    		MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(specimen.getSpecimenType(),"CAT_SP_TYPE");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			specimen.setSpecimenType(descCatalogo);
    		}
    	}
	    return "capture/specimens/uploadResult";
	}


	/**
	 * Custom handler for searhing.
	 *
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping( value="/search/", method=RequestMethod.GET)
	public @ResponseBody List<SpecimensResults> searchProcess(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
		SpecimensFilters specFilters = jsonToFilter(filtro);
		List<SpecimensResults> specimensList;



		List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
		List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
		List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
		List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");

		if (specFilters.getActiveSearch() == 0){
				specimensList = new ArrayList<>();

        }else{

			specimensList = specimenFilterService.getSpecimensByFilter(specFilters);

		}


		String lang = LocaleContextHolder.getLocale().getLanguage();
		for(SpecimensResults specimen:specimensList) {
			String descCatalogo = null;

			if(mr_sp_type!=null) {
				descCatalogo = getMessage(mr_sp_type, specimen.getSpecimenType(), lang);
				specimen.setSpecimenType(descCatalogo);
			}

			if(mr_sino!=null) {
				descCatalogo = getMessage(mr_sino, specimen.getInStorage(), lang);
				specimen.setInStorage(descCatalogo);
			}

			if(mr_vol_units!=null) {
				descCatalogo = getMessage(mr_vol_units, specimen.getVolUnits(), lang);
				specimen.setVolUnits(descCatalogo);
			}

			if(mr_sp_cond!=null) {
				descCatalogo = getMessage(mr_sp_cond, specimen.getSpecimenCondition(), lang);
				specimen.setSpecimenCondition(descCatalogo);
			}

			if(mr_sino!=null) {
				descCatalogo = getMessage(mr_sino, specimen.getDesPasive(), lang);
				specimen.setDesPasive("<span class='badge badge-"+(specimen.getDesPasive().equals("1")? "success":"danger")+"'>"+descCatalogo + "</span>");
			}



		}



		return specimensList ;
	}

    @RequestMapping( value="/aliquotView/", method=RequestMethod.GET)
    public @ResponseBody List<SpecimensResults> searchProcess1(@RequestParam( value = "strFilter", required = true) String filtro) throws Exception {
        SpecimensFilters specFilters = jsonToFilter(filtro);
        List<SpecimensResults> specimensList;



        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");

        if (specFilters.getActiveSearch() == 0){
            specimensList = new ArrayList<>();

        }else{

            specimensList = specimenFilterService.getSpecimensByFilter(specFilters);

        }


        String lang = LocaleContextHolder.getLocale().getLanguage();
        for(SpecimensResults specimen:specimensList) {
            String descCatalogo = null;

            if(mr_sp_type!=null) {
                descCatalogo = getMessage(mr_sp_type, specimen.getSpecimenType(), lang);
                specimen.setSpecimenType(descCatalogo);
            }

            if(mr_sino!=null) {
                descCatalogo = getMessage(mr_sino, specimen.getInStorage(), lang);
                specimen.setInStorage(descCatalogo);
            }

            if(mr_vol_units!=null) {
                descCatalogo = getMessage(mr_vol_units, specimen.getVolUnits(), lang);
                specimen.setVolUnits(descCatalogo);
            }

            if(mr_sp_cond!=null) {
                descCatalogo = getMessage(mr_sp_cond, specimen.getSpecimenCondition(), lang);
                specimen.setSpecimenCondition(descCatalogo);
            }

            if(mr_sino!=null) {
                descCatalogo = getMessage(mr_sino, specimen.getDesPasive(), lang);
                specimen.setDesPasive("<span class='badge badge-"+(specimen.getDesPasive().equals("1")? "success":"danger")+"'>"+descCatalogo + "</span>");
            }



        }



        return specimensList ;
    }

    @RequestMapping( value="/ClassExcForm1/", method = RequestMethod.GET)
    public String submitUploadForm( ModelMap modelmap) throws IOException {
    {
        List<SqlQueryToInventory> lista_entidades1;
        List<SqlQueryToInventory> specimensList1;

        SqlQueryToInventory entidad = new SqlQueryToInventory();
        List<SqlQueryToInventory> entidadesSQL = new ArrayList<SqlQueryToInventory>();

        List<MessageResource>  mr_sp_type = this.messageResourceService.getCatalogoTodos("CAT_SP_TYPE");
        List<MessageResource>  mr_sino = this.messageResourceService.getCatalogoTodos("CAT_SINO");
        List<MessageResource>  mr_vol_units = this.messageResourceService.getCatalogoTodos("CAT_VOL_UNITS");
        List<MessageResource>  mr_sp_cond = this.messageResourceService.getCatalogoTodos("CAT_SP_COND");

        lista_entidades1 = new ArrayList<SqlQueryToInventory>();
       // entidad  = DownSQLUpdateService.getSpecimensByFilter1().get(1);
        lista_entidades1 = (DownSQLUpdateService.getSpecimensByFilter1());

        //for(ClassExcForm ClassExcForm1:lista_entidades1) {
        //    System.out.println("lista_entidades1::" + ClassExcForm1.getOrthocode() );
        //}
        modelmap.addAttribute("lista_entidades1", lista_entidades1);

        List<Subject> subjects = this.subjectService.getActiveSubjects();

        //	model.addAttribute("entidades", entidades);
        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturespecimenspage");

        //  return "capture/specimens/uploadResultExcel";
        return "capture/specimens/list_Dquery";


    }
    }

        /**
	 * M�todo para convertir estructura Json que se recibe desde el cliente a FiltroMx para realizar b�squeda de Mx(Vigilancia) y Recepci�n Mx(Laboratorio)
	 *
	 * @param strJson String con la informaci�n de los filtros
	 * @return FiltroMx
	 * @throws Exception
	 */
	private SpecimensFilters jsonToFilter(String strJson) throws Exception {
		JsonObject jObjectFiltro = new Gson().fromJson(strJson, JsonObject.class);
		SpecimensFilters specFilters = new SpecimensFilters();
		String specimenId = null;
		String labReceiptDate = null;
		String orthocode = null;
		String studyId = null;
		String box = null;
        String estado = null;
		Integer activeSearch = null;


		if (jObjectFiltro.get("specimenId") != null && !jObjectFiltro.get("specimenId").getAsString().isEmpty())
			specimenId = jObjectFiltro.get("specimenId").getAsString();
		if (jObjectFiltro.get("labReceiptDate") != null && !jObjectFiltro.get("labReceiptDate").getAsString().isEmpty())
			labReceiptDate = jObjectFiltro.get("labReceiptDate").getAsString();
		if (jObjectFiltro.get("orthocode") != null && !jObjectFiltro.get("orthocode").getAsString().isEmpty())
			orthocode = jObjectFiltro.get("orthocode").getAsString();
		if (jObjectFiltro.get("studyId") != null && !jObjectFiltro.get("studyId").getAsString().isEmpty())
			studyId = jObjectFiltro.get("studyId").getAsString();
		if (jObjectFiltro.get("box") != null && !jObjectFiltro.get("box").getAsString().isEmpty())
			box = jObjectFiltro.get("box").getAsString();

        if (jObjectFiltro.get("estado") != null && !jObjectFiltro.get("estado").getAsString().isEmpty())
            estado = jObjectFiltro.get("estado").getAsString();

		if (jObjectFiltro.get("activeSearch") != null && !jObjectFiltro.get("activeSearch").getAsString().isEmpty())
			activeSearch = jObjectFiltro.get("activeSearch").getAsInt();


		specFilters.setSpecimenId(specimenId);
		specFilters.setOrthocode(orthocode);
		specFilters.setLabReceiptDate(labReceiptDate);
		specFilters.setStudyId(studyId);
		specFilters.setBox(box);
        specFilters.setEstado(estado);
		specFilters.setActiveSearch(activeSearch);

		return specFilters;
	}

	public static String getMessage(List<MessageResource> cat, String codigo, String idioma) {
		String etiqueta = "";
		try {
			for (MessageResource message : cat) {
				if (message.getCatKey().equalsIgnoreCase(codigo)) {
					etiqueta = idioma.equals("en") ? message.getEnglish(): message.getSpanish();
					break;
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return etiqueta;
	}


	@RequestMapping(value = "getBoxes", method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody
	List<Box> getBoxes(@RequestParam(value = "box", required = true) String box) throws Exception {
		return boxService.getActiveBoxesbyName(box);
	}

}

