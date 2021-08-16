package edu.berkeley.harrislab.samplemanager.domain;

/**
 * Clase para descargar la data para actualizar el inventario
 * Creado por: Everts Morales Reyes.
 */
public class SqlQueryToInventory {

    private String specimenId;
    private String orthocode;
    private String specimenCondition;
    private String volUnits;
    private Float volume;
    private Integer varA;
    private Integer varB;
    private String labReceiptDate;
    private String recordUser;
    private String specimenType;
    private Integer position;
    private String Study_id;
    private String Study_id2;
    private String box_id;
    private String Rack_name;
    private String Equip_id;
    private String Equip_name;





    public String getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(String specimenId) {
        this.specimenId = specimenId;
    }

    public String getOrthocode() {
        return orthocode;
    }

    public void setOrthocode(String orthocode) {
        this.orthocode = orthocode;
    }

    public String getSpecimenCondition() {
        return specimenCondition;
    }

    public void setSpecimenCondition (String specimenCondition) {
        this.specimenCondition = specimenCondition;
    }

    public String getVolUnits () {
        return volUnits;
    }

    public void setVolUnits (String volUnits) {
        this.volUnits = volUnits;
    }

    public Float getVolume () { return volume;   }

    public void setVolume  (Float volume) {
        this.volume = volume;
    }

    public Integer getVarA () {
        return varA;
    }

    public void setVarA (Integer varA) {
        this.varA = varA;
    }

    public Integer getVarB () {
        return varB;
    }

    public void setVarB (Integer varB) {
        this.varB = varB;
    }

    public String getLabReceiptDate() {
        return labReceiptDate;
    }

    public void setLabReceiptDate(String labReceiptDate) {
        this.labReceiptDate = labReceiptDate;
    }

    public String getRecordUser () {
        return recordUser;
    }

    public void setRecordUser (String recordUser) {
        this.recordUser = recordUser;
    }

    public String getSpecimenType () {
        return specimenType;
    }

    public void setSpecimenType (String specimenType) {
        this.specimenType = specimenType;
    }

    public Integer getPosition () {
        return position;
    }

    public void setPosition (Integer position) {
        this.position = position;
    }

    public String getStudy_id () {
        return Study_id;
    }

    public void setStudy_id (String Study_id) {
        this.Study_id = Study_id;
    }

    public String getStudy_id2 () {
        return Study_id2;
    }

    public void setStudy_id2 (String Study_id2) {
        this.Study_id2 = Study_id2;
    }

    public String getBox_id () {
        return box_id;
    }

    public void setBox_id (String box_id) {
        this.box_id = box_id;
    }

    public String  getRack_name () {
        return Rack_name;
    }

    public void setRack_name (String Rack_name) {
        this.Rack_name = Rack_name;
    }

    public String   getEquip_id () {
        return Equip_id;
    }

    public void setEquip_id(String Equip_id) {
        this.Equip_id = Equip_id;
    }

    public String getEquip_name () {
        return Equip_id;
    }

    public void setEquip_name(String Equip_name) {
        this.Equip_name = Equip_name;
    }

}
