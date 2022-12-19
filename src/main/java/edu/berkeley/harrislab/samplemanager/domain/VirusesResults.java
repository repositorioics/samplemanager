package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

public class VirusesResults {

    private String systemId;
    private String virusId;
    private String virus_Serotype;
    private String strain;
    private String batch_number;
    private String passage;
    private String experiment_id ;

    private String stage_of_production;
    private String amount_of_Unconc_virus;
    private Date date_Uncon_Collected;
    private Integer num_aliquots;
    private Float aliquot_volume;

    private String amount_of_Conc_virus;
    private Date date_of_Conc;
    private String amount_of_Purified;
    private Date date_of_Purification;
    private String qc_PCR_Check;
    private String qc_ELISA_Check;
    private String notes_on_QC;
    private String num_of_collections;
    private String day_Virus_Collected;

    private String cell_line_and_passage;
    private String viral_inoculum_vial_ID;
    private String bca_Concentration;
    private String fluorospot_check;
    private String notes_on_FS_check;
    private String viral_Titer;
    private String storage_temperature;


    private String loc_freezer;
    private String loc_rack;
    private String loc_box;
    private Integer loc_pos;

    private String comments;
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

    public String getVirusId() {
        return virusId;
    }

    public void setVirusId(String virusId) {
        this.virusId = virusId;
    }

    public String getVirus_Serotype() {
        return virus_Serotype;
    }

    public void setVirus_Serotype(String virus_Serotype) {
        this.virus_Serotype = virus_Serotype;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public String getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(String batch_number) {
        this.batch_number = batch_number;
    }

    public String getDate() {
        return passage;
    }

    public void setDate(String passage) {
        this.passage = passage;
    }

    public Float getAliquot_volume() {
        return aliquot_volume;
    }

    public void setAliquot_volume(Float aliquot_volume) {
        this.aliquot_volume = aliquot_volume;
    }

    public String getExperiment_id() {
        return experiment_id;
    }

    public void setExperiment_id(String experiment_id) {
        this.experiment_id = experiment_id;
    }


    public String getPassage() {
        return stage_of_production;
    }

    public void setPassage(String stage_of_production) {
        this.stage_of_production = stage_of_production;
    }


    public String getAmount_of_Unconc_virus() {
        return amount_of_Unconc_virus;
    }

    public void setAmount_of_Unconc_virus(String amount_of_Unconc_virus) {
        this.amount_of_Unconc_virus = amount_of_Unconc_virus;
    }

    public Date getDate_Uncon_Collected() {
        return date_Uncon_Collected;
    }

    public void setDate_Uncon_Collected(Date date_Uncon_Collected) {
        this.date_Uncon_Collected = date_Uncon_Collected;
    }

    public Integer getNum_aliquots() {
        return num_aliquots;
    }

    public void setNum_aliquots(Integer num_aliquots) {
        this.num_aliquots = num_aliquots;
    }

    public String getAmount_of_Conc_virus() {
        return amount_of_Conc_virus;
    }

    public void setAmount_of_Conc_virus(String amount_of_Conc_virus) {
        this.amount_of_Conc_virus = amount_of_Conc_virus;
    }

    public Date getDate_of_Conc() {
        return date_of_Conc;
    }

    public void setDate_of_Conc(Date date_of_Conc) {
        this.date_of_Conc = date_of_Conc;
    }


    public String getAmount_of_Purified() {
        return amount_of_Purified;
    }

    public void setAmount_of_Purified(String amount_of_Purified) {
        this.amount_of_Purified = amount_of_Purified;
    }


    public Date getDate_of_Purification() {
        return date_of_Purification;
    }

    public void setDate_of_Purification(Date date_of_Purification) {
        this.date_of_Purification = date_of_Purification;
    }


    public String getQc_PCR_Check() {
        return qc_PCR_Check;
    }

    public void setQc_PCR_Check(String qc_PCR_Check) {
        this.qc_PCR_Check = qc_PCR_Check;
    }

    public String getQc_ELISA_Check() {
        return qc_ELISA_Check;
    }

    public void setQc_ELISA_Check(String qc_ELISA_Check) {
        this.qc_ELISA_Check = qc_ELISA_Check;
    }

    public String getNotes_on_QC() {
        return notes_on_QC;
    }

    public void setNotes_on_QC(String notes_on_QC) {
        this.notes_on_QC = notes_on_QC;
    }

    public String getNum_of_collections() {
        return num_of_collections;
    }

    public void setNum_of_collections(String num_of_collections) {
        this.num_of_collections = num_of_collections;
    }

    public String getStage_of_production() {
        return day_Virus_Collected;
    }

    public void setDay_Virus_Collected(String day_Virus_Collected) {
        this.day_Virus_Collected = day_Virus_Collected;
    }

    public String getCell_line_and_passage() {
        return cell_line_and_passage;
    }

    public void setCell_line_and_passage(String cell_line_and_passage) {
        this.cell_line_and_passage = cell_line_and_passage;
    }

    public String getViral_inoculum_vial_ID() {
        return viral_inoculum_vial_ID;
    }

    public void setViral_inoculum_vial_ID(String viral_inoculum_vial_ID) {
        this.viral_inoculum_vial_ID = viral_inoculum_vial_ID;
    }

    public String getBca_Concentration() {
        return bca_Concentration;
    }

    public void setBca_Concentration(String bca_Concentration) {
        this.bca_Concentration = bca_Concentration;
    }

    public String getFluorospot_check() {
        return fluorospot_check;
    }

    public void setFluorospot_check(String fluorospot_check) {
        this.fluorospot_check = fluorospot_check;
    }

    public String getNotes_on_FS_check() {
        return notes_on_FS_check;
    }

    public void setNotes_on_FS_check(String notes_on_FS_check) {
        this.notes_on_FS_check = notes_on_FS_check;
    }

    public String getViral_Titer() {
        return viral_Titer;
    }

    public void setViral_Titer(String viral_Titer) {
        this.viral_Titer = viral_Titer;
    }

    public String getStorage_temperature() {
        return storage_temperature;
    }

    public void setStorage_temperature(String storage_temperature) {
        this.storage_temperature = storage_temperature;
    }


    public String getLoc_freezer() {
        return loc_freezer;
    }

    public void setLoc_freezer(String  loc_freezer) {
        this.loc_freezer = loc_freezer;
    }

    public String getLoc_rack() {
        return loc_rack;
    }

    public void setLoc_rack(String  loc_rack) {
        this.loc_rack = loc_rack;
    }

    public String getLoc_box() {
        return loc_box;
    }

    public void setLoc_box(String  loc_box) {
        this.loc_box = loc_box;
    }

    public Integer getLoc_pos () {
        return loc_pos;
    }

    public void setLoc_pos(Integer  loc_pos) {
        this.loc_pos = loc_pos;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String  comments) {
        this.comments = comments;
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
