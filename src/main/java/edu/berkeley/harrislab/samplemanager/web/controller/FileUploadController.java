package edu.berkeley.harrislab.samplemanager.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.functions.FuncSubstring;
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

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;





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
        List<String> codigos_error = new ArrayList<String>();
        List<String> codigos_error_mensaje = new ArrayList<String>();
        //WebAuthenticationDetails wad  = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
       // UserSistema usuarioActual = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

        try {
            //Read the file
            Reader in = new InputStreamReader(file.getInputStream());
            InputStream in1 = file.getInputStream();
            //Define the format
          //  String fileLocation;
           // File currDir = new File(".");
           // String path = currDir.getAbsolutePath();
           // fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
          //  FileOutputStream f = new FileOutputStream(fileLocation);
           // int ch = 0;
           // while ((ch = in1.read()) != -1) {
            //    f.write(ch);
        //    }
      //     f.flush();
          //  f.close();


          //  FileInputStream file2 = new FileInputStream(new File(fileLocation));

            //Crear Workbook para instanciar referencia a .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(in1);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);


            int numFilas = sheet.getLastRowNum();
            int numNoact = 0;
            //   int numColum = sheet.getActiveCell().toString() ;

            modelMap.addAttribute("numFilas", numFilas-1);
            modelMap.addAttribute("numNoact", numNoact);
            int numcol = 0;

            // HSSFRow hssfRow;
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int contc = 0;
            int contfinalrows = 1;
            int camponoexiste = 0;
            int centinelafin = 0;
            //   while (rowIterator.hasNext()  ) {

            Row row = rowIterator.next();
            //For each row, iterate through all the columns
            contc = contc + 1 ;
            Cell c1 = row.getCell(contc);





            Iterator<Cell> cellIterator = row.cellIterator();
            int contf = 1;
            numcol = row.getLastCellNum();
            while (numFilas > contc) {

                int campoexiste = 0;
                Cell c = row.getCell(contf);
                //  Cell cell = cellIterator.next() ;

                if ((contf != contfinalrows) && (c.getCellType() != null ) ){
                    /**  if (contc > 3)  {
                     contc = 2;
                     }**/
                    contfinalrows = contfinalrows - 1;
                }

                    contf = 1;
                    contc = contc + 1;


                if  (sheet.getRow(1).getCell(contf) != null)  {

                    /**Identificamos la tabla y el id
                     cada columna es un campo mas a actualizar
                     en sheet el getrow 0 son los encabezados
                     el encabezado de cada columna es el nombre de cada campo a partir de la columna 1**/

                    if  (sheet.getRow(1).getCell(contf) != null)  {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("specimenId")) {
                            specimenId = sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim();
                            entidad = this.specimenService.getSpecimenByUserId(specimenId);
                            // entidad_error = this.specimenService.getSpecimenByUserId(specimenId);
                            if (entidad != null && !specimenId.toString().isEmpty()) {
                                Date objDate = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                Date fechaIngr = null;
                                LocalDate date1 = LocalDate.now();
                                Date fec = null;
                                fec = formatter.parse(date1.toString());
                                entidad.setRecordDate(fec);

                                entidad.setSpecimenId(specimenId);



                                contfinalrows = contfinalrows + 1;
                                entidad_error.setObs("0");
                                contf++;

                            } else {
                                entidad_error.setSpecimenId(specimenId);
                                entidad_error.setObs("Error: specimenId, ");
                               // entidades_error.add(numNoact,entidad_error);
                                codigos_error.add(numNoact,specimenId + "  Error: specimenId, not found ");
                               // codigos_error.add(numNoact,"Error: specimenId, not found ");
                                numNoact = numNoact + 1;
                                numFilas = numFilas - 1;
                                modelMap.addAttribute("numNoact", numNoact);
                                modelMap.addAttribute("numFilas", numFilas - numNoact);
                                modelMap.addAttribute("codigos_error", codigos_error);
                                entidad_error.setSpecimenId("");

                            }
                        }
                        campoexiste ++;
                    }
                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("specimenType")) {

                            specimenType = sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            //specimenTypeCatKey = this.messageResourceService.getMensajeDesc(specimenType, "CAT_SP_TYPE").getCatKey();
                            if (entidad != null && !specimenType.toString().isEmpty()) {
                                entidad.setSpecimenType(specimenType);
                                contfinalrows = contfinalrows + 1;
                                contf++;
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: specimenType, ");
                            }
                            campoexiste++;
                        }
                    }


                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("Record_user")) {
                            if (!sheet.getRow(contc).getCell(contf).toString().equals("")) {
                                Record_user = sheet.getRow(contc).getCell(contf).toString();
                                contf++;
                            }
                            if ((entidad != null) && !Record_user.toString().isEmpty()) {
                                entidad.setRecordUser(Record_user);
                                contfinalrows = contfinalrows + 1;
                                contf++;
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: inStorage, ");
                            }

                            campoexiste++;
                        }
                    }
                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("Record_date")) {
                            if (!sheet.getRow(contc).getCell(contf).toString().equals("")) {
                                Record_user = sheet.getRow(contc).getCell(contf).toString();
                                contf++;
                            }
                            if ((entidad != null) && !Record_date.toString().isEmpty()) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                entidad.setRecordDate(formatter.parse(Record_date));
                                contfinalrows = contfinalrows + 1;
                                contf++;
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: inStorage, ");
                            }

                            campoexiste++;
                        }
                    }
                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("specimenCondition")) {

                            specimenCondition = sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null && !specimenCondition.toString().isEmpty()) {
                                entidad.setSpecimenCondition(specimenCondition);
                                contfinalrows = contfinalrows + 1;
                                contf++;
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: specimenCondition, ");
                            }
                            campoexiste++;
                        }
                    }
                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("orthocode")) {

                            orthocode = sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                            if (entidad != null && !orthocode.toString().isEmpty()) {
                                entidad.setOrthocode(orthocode);
                                contfinalrows = contfinalrows + 1;
                                contf++;
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: orthocode, ");
                            }
                            campoexiste++;
                        }
                    }
                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("volume")) {
                            if (c != null) {
                                volume = Float.parseFloat("0");
                                if (sheet.getRow(contc).getCell(contf).toString() != "") {
                                    volume = Float.parseFloat(sheet.getRow(contc).getCell(contf).toString());
                                    entidad.setVolume(volume);
                                    contf++;
                                    if (volume == 0)
                                    {
                                        SpecimenStorage entidad_storage1 = this.specimenStorageService.getSpecimenStorageBySpecId(entidad.getSystemId());

                                        entidad.setEstado("0");
                                        entidad.setInStorage("0");
                                        entidad_storage1.setPos(0);
                                        entidad_storage1.setBox(null);
                                        entidad_storage1.setSpecimen(null);

                                    }
                                    if (volume > 0)
                                    {
                                        entidad.setEstado("1");
                                    }

                                    this.specimenService.saveSpecimen(entidad);
                                }
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: volume, ");
                                volume = Float.parseFloat("-1");
                            }
                            if (entidad != null && volume != -1) {
                                entidad.setVolume(volume);
                                // contfinalrows = contfinalrows + 1;
                            } else {
                                entidad_error.setObs(entidad_error.getObs() + " Error: volume, ");
                            }
                            campoexiste++;
                        }
                    }
                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("inStorage")) {
                            if (!sheet.getRow(contc).getCell(contf).toString().equals("")) ;
                            {
                                inStorage = sheet.getRow(contc).getCell(contf).toString();


                                contf++;
                            }
                            if ((entidad != null) && !inStorage.toString().isEmpty() && entidad.getVolume() != 0 ) {
                                entidad.setInStorage(inStorage);
                                try {
                                    SpecimenStorage entidad_storage = this.specimenStorageService.getSpecimenStorageBySpecId(entidad.getSystemId());

                                Equip entidad_freezer = this.equipService.getEquipByUserId(sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
                                if (entidad_freezer != null)
                                    {

                                Rack entidad_rack = this.rackService.getRackByUserId(sheet.getRow(contc).getCell(contf+1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
                                        if (entidad_rack != null)
                                        {

                                            entidad_rack.setEquip(entidad_freezer);

                                            Box entidad_box = this.boxService.getBoxByUserId(sheet.getRow(contc).getCell(contf + 2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim());
                                            if (entidad_box != null)
                                            {

                                            entidad_box.setRack(entidad_rack);

                                            entidad_storage.setBox(entidad_box);

                                            int pos2 = 0;
                                            //     try {
                                            String position, pos1 = "";

                                            position = sheet.getRow(contc).getCell(contf + 3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                                            for (int b = 0; b < position.length(); b++) {
                                                if (position.charAt(b) == '.') {
                                                    pos1 = position.substring(0, b);
                                                }
                                            }
                                            pos2 = Integer.parseInt(pos1);

                                            // entidad_storage.setPos(pos2);


                                            //  rackService.saveRack(entidad_rack);
                                            //  boxService.saveBox(entidad_box);

                                            SpecimenStorage entidad2 = null;
                                            //  entidad2 = new SpecimenStorage(new Date(), usuarioActual.getUsername(), wad.getRemoteAddress(), '0');
                                            // entidad2.setSpecimen(entidad);
                                            entidad_storage.setPos(pos2);

                                            entidad_storage.setBox(this.boxService.getBoxBySystemId(entidad_box.getSystemId()));

                                            this.specimenStorageService.updateSpecimenStorage(entidad_storage);
                                            } else
                                            {
                                                codigos_error.add(numNoact, sheet.getRow(contc).getCell(contf + 2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim() + "  Error: Box, not found ");
                                                // codigos_error.add(numNoact,"Error: specimenId, not found ");
                                                numNoact = numNoact + 1;

                                            }//fin if de box si no existe
                                        }else
                                        {
                                            codigos_error.add(numNoact, sheet.getRow(contc).getCell(contf + 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim() + "  Error: Rack, not found ");
                                            // codigos_error.add(numNoact,"Error: specimenId, not found ");
                                            numNoact = numNoact + 1;

                                        }//fin if de rack si no existe
                                    }else
                                    {
                                        codigos_error.add(numNoact,sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim() + "  Error: Freezer, not found ");
                                        // codigos_error.add(numNoact,"Error: specimenId, not found ");
                                        numNoact = numNoact + 1;

                                    }//fin if de freezer si no existe



                              //  } catch (NumberFormatException nfe) {
                                    // Handle the condition when str is not a number.
                                //    logger.error(nfe.getLocalizedMessage());
                              //  }
                                }  catch (NumberFormatException nfe) {
                                    // Handle the condition when str is not a number.
                                    logger.error(nfe.getLocalizedMessage());
                                    entidad_error.setObs(entidad_error.getObs() + " Error: volume, ");
                                }
                                contfinalrows = contfinalrows + 1;
                            } else {
                                codigos_error.add(entidad.getSpecimenId() + "  Error: storage id indicates that it is not stored or volume is zero ");
                                // codigos_error.add(numNoact,"Error: specimenId, not found ");
                                numNoact = numNoact + 1;
                            }

                            campoexiste++;
                        }
                    }

                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("varA")) {
                            if (sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString() != "") {
                                varA = Integer.parseInt(sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                                contf++;
                            } else {
                                varA = 0;
                            }

                            if (entidad != null) {
                                entidad.setVarA(varA);
                                contfinalrows = contfinalrows + 1;
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: varA, ");
                            }
                            campoexiste++;
                        }
                    }

                    if  (sheet.getRow(1).getCell(contf) != null) {
                        if (sheet.getRow(1).getCell(contf).toString().equalsIgnoreCase("varB")) {
                            if (sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString() != "") {
                                varB = Integer.parseInt(sheet.getRow(contc).getCell(contf, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                                contf++;
                            } else {
                                varB = 0;
                            }

                            if (entidad != null) {
                                entidad.setVarB(varB);
                                contfinalrows = contfinalrows + 1;
                            } else {
                                entidad_error.setObs(entidad_error.getSpecimenId() + "  " + entidad_error.getObs() + " Error: varB, ");
                            }
                            campoexiste++;
                        }
                    }
                }

                if (c == null ) {
                    // cell.setBlank();
                    contfinalrows = contf + contfinalrows;
                }
                if (contf > 2){
                 //   this.specimenService.saveSpecimen(entidad);
                    entidad = null;
                }
                if (contf == 1) {
                    contf = 3;
                }
                //Se valida que el campo que se esta leyendo en el archivo de excel existe en la entidad
                   /* if (campoexiste == 0 && (c != null )) {
                       // entidades.add(entidad);

                        contf ++;
                        camponoexiste ++;
                        contfinalrows = contfinalrows + 1;
                    }else {
                        if (c != null ) {

                        //    entidad = null;
                        contf ++;
                        contfinalrows = contfinalrows + 1;
                        }

                    }*/
                centinelafin = centinelafin + 1;
            }

            //se actualizan los datos de cada registro del archivo de excel
            if ( entidad != null) {

                entidad_error.setObs("     Successfully updated records      ") ;
               // entidades_error.add(entidad_error);
                entidades.add(entidad);

                // entidad = null;
            }

            //contc++;
            contfinalrows = 1;
            //  centinelafin = centinelafin + 1;
            //  } FIN DE PRIMER WHILE
            if (entidad_error.getObs().toString().equals("0") )
            {
                entidad_error.setObs("     Successfully updated records      ") ;
            }

            modelMap.addAttribute("entidades", entidades);

          //  modelMap.addAttribute("entidadesErr", entidades_error);

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

      //  modelMap.addAttribute("entidadesErr", entidades);

        return "capture/specimens/uploadResultExcel";
    }


}

