package edu.berkeley.harrislab.samplemanager.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mysql.jdbc.ExportControlled;
import edu.berkeley.harrislab.samplemanager.domain.*;
import edu.berkeley.harrislab.samplemanager.domain.audit.AuditTrail;
import edu.berkeley.harrislab.samplemanager.language.MessageResource;
import edu.berkeley.harrislab.samplemanager.service.*;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;

import javax.servlet.ServletOutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamResource;
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
import org.apache.poi.xssf.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.rmi.server.ExportException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.IOException;

import java.net.URLEncoder;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;




/**
 * Created by ICS_DESARROLLO1 on 16/6/2021.
 */
@Controller
@RequestMapping("/admin/fileuploadexcell/*")
public class FileUploadController {

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String obtenerEntidades(Model model) throws ParseException {
        logger.debug("Mostrando registros en JSP");


        List<Subject> subjects = this.subjectService.getActiveSubjects();
        model.addAttribute("subjects",subjects);
        //	model.addAttribute("entidades", entidades);
        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"capturespecimenspage");
        return "admin/fileuploadexcell/uploadExcel";
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
    @RequestMapping(value = "/uploadEntity/", method = RequestMethod.GET )
    public String initUploadForm(Model model) {
        this.visitsService.saveUserPages(this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()),new Date(),"captureuploadspecimenpage");
        return "capture/specimens/uploadExcel";
    }

    @RequestMapping(value = "/excelDowned/")
    /**
     * @Description: descargue la plantilla de Excel
     * @author       : bjh
     * @param        : [response]
     * @return       : void
     * @exception    :
     * @date         : 2018/11/13 15:27
     */
    public void excelDowned(HttpServletResponse  response){
        try {
          /**  ServletOutputStream  outputStream = response.getOutputStream();
            String filename = "SpecimensTemplate";
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");**/
           // response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            ServletOutputStream  outputStream = response.getOutputStream();
            String filename = "SpecimensTemplate.xls";
            FileOutputStream fileOut;
            fileOut = new FileOutputStream("report.xlsx");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Transfer-Encoding", "binary");
           response.setCharacterEncoding("utf-8");
            // Encabezado de tabla
            String title = "Specimens Update Template";
            // Número de filas y columnas.
            int rows = 2;
            int cols = 18;
            //response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            //response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // Formulario
          //  String[][] excelData = new String[rows][cols];
            /***********************************
             Rellene el contenido de excelData
             Nota: Los datos se establecen desde la primera línea y la línea 0 se usa para establecer el encabezado.
             )
             **************************************/

            // Se crea el libro
            HSSFWorkbook libro = new HSSFWorkbook();

            // Se crea una hoja dentro del libro
            Sheet sheet = libro.createSheet("SpecimenTemplate");



            // Se crea una celda dentro de la fila
           Row row = sheet.createRow(0);

                    row.createCell(0).setCellValue("SpecimenID");
            row.createCell(1).setCellValue("Specimen Type");
            row.createCell(2).setCellValue("Volume Units");

             Cell cel = row.createCell(3);

             cel.setCellFormula(String.format("",""));

            // Se salva el libro.
            try {
               //FileOutputStream elFichero = new FileOutputStream("SpecimensTemplate");
                //libro.write(elFichero);
                //elFichero.close();

                libro.write(fileOut );
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**carga el archivo excel**/

    @RequestMapping(value = "/uploadEntityFile/", method = RequestMethod.POST)
    public String submitUploadForm(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        boolean checkLabReceiptDate = false;
        String specimenId;
        String orthocode, Record_user;
        String specimenCondition;
        Float volume;
        Integer varA;
        Integer varB;
        String specimenType,specimenTypeCatKey,inStorage;
        String Record_date;
        int nuevos =0, viejos=0;

        Record_user = "";
        Record_date = "";
        Specimen entidad = new Specimen();
        Specimen entidad_error = new Specimen();
        List<Specimen> entidades = new ArrayList<Specimen>();
        List<Specimen> entidades_error = new ArrayList<Specimen>();
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


                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("specimenId")){
                            specimenId = sheet.getRow(contc).getCell(contf,  Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            entidad = this.specimenService.getSpecimenByUserId(specimenId);
                            entidad_error = this.specimenService.getSpecimenByUserId(specimenId);
                            if (entidad != null && !specimenId.toString().isEmpty()) {
                                entidad.setSpecimenId(specimenId);
                                entidad_error.setSpecimenId(specimenId);
                                contfinalrows = contfinalrows + 1;
                                entidad_error.setObs("0");
                            }else
                            {
                                entidad_error.setObs("Error: specimenId, ") ;
                            }
                            campoexiste ++;
                        }
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("specimenType")){

                            specimenType = sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            //specimenTypeCatKey = this.messageResourceService.getMensajeDesc(specimenType, "CAT_SP_TYPE").getCatKey();
                            if (entidad != null && !specimenType.toString().isEmpty()) {
                                entidad.setSpecimenType(specimenType);
                                contfinalrows = contfinalrows + 1;
                            }
                            else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: specimenType, ") ;
                            }
                            campoexiste ++;
                        }
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("inStorage")){
                            if (!sheet.getRow(contc).getCell(contf).toString().equals(""));
                            {
                                inStorage = sheet.getRow(contc).getCell(contf).toString();
                            }
                            if ((entidad != null) && !inStorage.toString().isEmpty()) {
                                entidad.setInStorage(inStorage);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: inStorage, ") ;
                            }

                            campoexiste ++;
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("Record_user")){
                            if (!sheet.getRow(contc).getCell(contf).toString().equals("")) {
                                Record_user = sheet.getRow(contc).getCell(contf).toString();
                            }
                            if ((entidad != null) && !Record_user.toString().isEmpty()) {
                                entidad.setRecordUser(Record_user);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: inStorage, ") ;
                            }

                            campoexiste ++;
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("Record_date")){
                            if (!sheet.getRow(contc).getCell(contf).toString().equals("")) {
                                Record_user = sheet.getRow(contc).getCell(contf).toString();
                            }
                            if ((entidad != null) && !Record_date.toString().isEmpty()) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                entidad.setRecordDate(formatter.parse(Record_date));
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: inStorage, ") ;
                            }

                            campoexiste ++;
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("specimenCondition")){

                            specimenCondition = sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null && !specimenCondition.toString().isEmpty()) {
                                entidad.setSpecimenCondition(specimenCondition) ;
                                  contfinalrows = contfinalrows + 1;
                            }
                            else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: specimenCondition, ") ;
                            }
                            campoexiste ++;
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("orthocode")){

                            orthocode = sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null && !orthocode.toString().isEmpty()) {
                                entidad.setOrthocode(orthocode);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: orthocode, ") ;
                            }
                            campoexiste ++;
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("volume")){
                            if (c != null) {
                                volume = Float.parseFloat("0");
                               if (sheet.getRow(contc).getCell(contf).toString()!=""){
                                volume = Float.parseFloat(sheet.getRow(contc).getCell(contf).toString());
                                   entidad.setVolume(volume);
                               }
                            }else{
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: volume, ") ;
                                volume= Float.parseFloat("-1");
                            }
                            if (entidad != null && volume != -1) {
                                entidad.setVolume(volume);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                entidad_error.setObs(entidad_error.getObs() +" Error: volume, ") ;
                            }
                            campoexiste ++;
                        }

                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("varA")){
                            if (sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()!="") {
                                varA = Integer.parseInt(sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            }else{
                                varA= 0;
                            }

                            if (entidad != null) {
                                entidad.setVarA(varA);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: varA, ") ;
                            }
                            campoexiste ++;
                        }
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("varB")){
                            if (sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()!="") {
                                varB = Integer.parseInt(sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                            }else{
                                varB= 0;
                            }

                            if (entidad != null) {
                                entidad.setVarB(varB);
                                contfinalrows = contfinalrows + 1;
                            }else
                            {
                                entidad_error.setObs(entidad_error.getSpecimenId() +"  "+entidad_error.getObs() +" Error: varB, ") ;
                            }
                            campoexiste ++;
                        }
                    }

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
                this.specimenService.saveSpecimen(entidad);
                    entidad_error.setObs("     Successfully updated records      ") ;
                    entidades_error.add(entidad_error);
                entidades.add(entidad);

                entidad = null;
                }

                //contc++;
                contfinalrows = 1;
              //  centinelafin = centinelafin + 1;
            }
            if (entidad_error.getObs().toString().equals("0") )
            {
                entidad_error.setObs("     Successfully updated records      ") ;
            }

            modelMap.addAttribute("entidades", entidades);

            modelMap.addAttribute("entidadesErr", entidades);

        }
        catch(IllegalArgumentException ile) {
            logger.error(ile.getLocalizedMessage());
            modelMap.addAttribute("importError", true);
            modelMap.addAttribute("errorMessage", ile.getLocalizedMessage());
            modelMap.addAttribute("entidades", entidades);
            return "capture/specimens/uploadResulExcelt";
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            modelMap.addAttribute("importError", true);
            modelMap.addAttribute("errorMessage", e.getMessage());
            modelMap.addAttribute("entidades", entidades);
            return "capture/specimens/uploadResultExcel";
        }

        for(Specimen specimen:entidades) {
            MessageResource mr = null;
            String descCatalogo = null;
            mr = this.messageResourceService.getMensaje(specimen.getSpecimenType(),"CAT_SP_TYPE");
            if(mr!=null) {
                descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
                specimen.setSpecimenType(descCatalogo);
            }
        }

        modelMap.addAttribute("entidadesErr", entidades);

        return "capture/specimens/uploadResultExcel";
    }


}

