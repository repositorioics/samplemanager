package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

public class RvpsResults {

    private String systemId;
    private String rvpId;
    private String batchName;
    private String virusSerotype;
    private String provider;
    private Date date;
    private Float aliquot_volume;

    private Integer num_aliq;
    private String working_dilution;
    private String infection_percentage;
    private String cellType;
    private String Time_hpi;

    private String comments;
    private String loc_freezer;
    private String loc_rack;
    private String loc_box;
    private Integer loc_pos;

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

    public String getRvpId() {
        return rvpId;
    }

    public void setRvpId(String rvpId) {
        this.rvpId = rvpId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String gt() {
        return virusSerotype;
    }

    public void setSequencing_confirmed(String virusSerotype) {
        this.virusSerotype = virusSerotype;
    }

    public String getProvider() {
        return provider;
    }

    public void setVirusSerotype(String provider) {
        this.provider = provider;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getAliquot_volume() {
        return aliquot_volume;
    }

    public void setAliquot_volume(Float aliquot_volume) {
        this.aliquot_volume = aliquot_volume;
    }

    public Integer getNum_aliq() {
        return num_aliq;
    }

    public void setNum_aliq(Integer num_aliq) {
        this.num_aliq = num_aliq;
    }


    public String getWorking_dilution() {
        return working_dilution;
    }

    public void setWorking_dilution(String working_dilution) {
        this.working_dilution = working_dilution;
    }


    public String getInfection_percentage() {
        return infection_percentage;
    }

    public void setInfection_percentage(String infection_percentage) {
        this.infection_percentage = infection_percentage;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public String getTime_hpi() {
        return Time_hpi;
    }

    public void setTime_hpi(String Time_hpi) {
        this.Time_hpi = Time_hpi;
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
