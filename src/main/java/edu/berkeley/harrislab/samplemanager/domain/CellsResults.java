package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

public class CellsResults {

    private String systemId;
    private String cellsId;
    private String cell_line_name;
    private Integer passage_number;
    private Date date_frozen;
    private String operator_name;
    private String company_name ;

    private String catalog_code;
    private String type_of_culture;   //seleccion adherent , suspension
    private String original_cell_type;
    private String original_organ;
    private String organism_name;

    private String cell_density;
    private Float volume_mL;
    private String freezing_medium;
    private String culture_media;
    private String QC_status;   //seleccion YES, NO
    private String QC_test_name; // Virus (DENV,ZIKV, OTHER (Bacteria,Mycoplasma,Cell identity))
    private String genetic_modification; // YES (transfection,transduction), NO
    private String selection_reagent;
    private String selection_concentration;

    private String freezer_name;
    private String rack_name;
    private String box_name;
    private Integer position_in_the_box;

    private String recordUser;
    private String recordDate;
    private String recordIp;
    private char pasive;
    private String subjectName;
    private String desPasive;


    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getCellsId() {
        return cellsId;
    }
    public void setCellsId(String cellsId) {
        this.cellsId = cellsId;
    }

    public String getCell_line_name() {
        return cell_line_name;
    }
    public void setCell_line_name(String cell_line_name) {
        this.cell_line_name = cell_line_name;
    }

    public Integer getPassage_number() {
        return passage_number;
    }
    public void setPassage_number(Integer passage_number) {
        this.passage_number = passage_number;
    }

    public Date getDate_frozen() {
        return date_frozen;
    }
    public void setDate_frozen(Date date_frozen) {
        this.date_frozen = date_frozen;
    }

    public String getOperator_name() {
        return operator_name;
    }
    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getCompany_name() {
        return company_name;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCatalog_code() {
        return catalog_code;
    }
    public void setCatalog_code(String catalog_code) {
        this.catalog_code = catalog_code;
    }


    public String getType_of_culture() {
        return type_of_culture;
    }
    public void setType_of_culture(String type_of_culture) {
        this.type_of_culture = type_of_culture;
    }


    public String getOriginal_cell_type() {
        return original_cell_type;
    }
    public void setOriginal_cell_type(String original_cell_type) {
        this.original_cell_type = original_cell_type;
    }

    public String getOriginal_organ() {
        return original_organ;
    }
    public void setOriginal_organ(String original_organ) {
        this.original_organ = original_organ;
    }

    public String getOrganism_names() {
        return organism_name;
    }
    public void setOrganism_names(String organism_name) {
        this.organism_name = organism_name;
    }

    public String getCell_density() {
        return cell_density;
    }
    public void setCell_density(String cell_density) {
        this.cell_density = cell_density;
    }

    public Float getVolume_mL() {
        return volume_mL;
    }
    public void setVolume_mL(Float volume_mL) {
        this.volume_mL = volume_mL;
    }

    public String getFreezing_medium() {
        return freezing_medium;
    }
    public void setFreezing_medium(String freezing_medium) {
        this.freezing_medium = freezing_medium;
    }

    public String getCulture_media() {
        return culture_media;
    }
    public void setCulture_media(String culture_media) {
        this.culture_media = culture_media;
    }

    public String getQC_status() {
        return QC_status;
    }
    public void setQC_status(String QC_status) {
        this.QC_status = QC_status;
    }

    public String getQC_test_name() {
        return QC_test_name;
    }
    public void setQC_test_name(String QC_test_name) {
        this.QC_test_name = QC_test_name;
    }

    public String getGenetic_modification() {
        return genetic_modification;
    }
    public void setGenetic_modification(String genetic_modification) {
        this.genetic_modification = genetic_modification;
    }

    public String getSelection_reagent() {
        return selection_reagent;
    }
    public void setSelection_reagent(String selection_reagent) {
        this.selection_reagent = selection_reagent;
    }

    public String getSelection_concentration() {
        return selection_concentration;
    }
    public void setSelection_concentration(String selection_concentration) {
        this.selection_concentration = selection_concentration;
    }

    public String getFreezer_name() {
        return freezer_name;
    }
    public void setFreezer_name(String  freezer_name) {
        this.freezer_name = freezer_name;
    }

    public String getRack_name() {
        return rack_name;
    }
    public void setRack_name(String  rack_name) {
        this.rack_name = rack_name;
    }

    public String getBox_name() {
        return box_name;
    }
    public void setBox_name(String  box_name) {
        this.box_name = box_name;
    }

    public Integer getPosition_in_the_box () {
        return position_in_the_box;
    }
    public void setPosition_in_the_box(Integer  position_in_the_box) {
        this.position_in_the_box = position_in_the_box;
    }



    public String getRecordUser() {
        return recordUser;
    }

    public void setRecordUser(String recordUser) {
        this.recordUser = recordUser;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordIp() {
        return recordIp;
    }

    public void setRecordIp(String recordIp) {
        this.recordIp = recordIp;
    }

    public char getPasive() {
        return pasive;
    }

    public void setPasive(char pasive) {
        this.pasive = pasive;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDesPasive() {
        return desPasive;
    }

    public void setDesPasive(String desPasive) {
        this.desPasive = desPasive;
    }

}
