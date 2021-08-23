package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

public class SpecimensResults {

    private String specimenId;
    private String systemId;
    private String orthocode;
    private String labReceiptDate;
    private String subjectId;
    private String specimenType;
    private String specimenCondition;
    private Float volume;
    private String volUnits;
    private String inStorage;
    private Integer varA;
    private Integer varB;
    private String obs;
    private String recordUser;
    private String recordDate;
    private String recordIp;
    private char pasive;
    private String subjectName;
    private String desPasive;

    private String Equip;
    private String Rack;
    private String Box;
    private Integer pos;


    public String getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(String specimenId) {
        this.specimenId = specimenId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getOrthocode() {
        return orthocode;
    }

    public void setOrthocode(String orthocode) {
        this.orthocode = orthocode;
    }

    public String getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(String specimenType) {
        this.specimenType = specimenType;
    }

    public String getSpecimenCondition() {
        return specimenCondition;
    }

    public void setSpecimenCondition(String specimenCondition) {
        this.specimenCondition = specimenCondition;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public String getVolUnits() {
        return volUnits;
    }

    public void setVolUnits(String volUnits) {
        this.volUnits = volUnits;
    }

    public String getInStorage() {
        return inStorage;
    }

    public void setInStorage(String inStorage) {
        this.inStorage = inStorage;
    }

    public Integer getVarA() {
        return varA;
    }

    public void setVarA(Integer varA) {
        this.varA = varA;
    }

    public Integer getVarB() {
        return varB;
    }

    public void setVarB(Integer varB) {
        this.varB = varB;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getLabReceiptDate() {
        return labReceiptDate;
    }

    public void setLabReceiptDate(String labReceiptDate) {
        this.labReceiptDate = labReceiptDate;
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public String getEquip() {
        return Equip;
    }

    public void setEquip(String Equip) {
        this.Equip = Equip;
    }

    public String getRack() {
        return Rack;
    }

    public void setRack(String Rack) {
        this.Rack = Rack;
    }

    public String getBox() {
        return Box;
    }

    public void setBox(String Box) {
        this.Box = Box;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer  pos) {
        this.pos = pos;
    }
}
