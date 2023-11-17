package edu.berkeley.harrislab.samplemanager.domain;

public class AliquotsFilters {

    private String specimenId;
    private String aliquotId;
    private String orthocode;
    private String labReceiptDate;
    private String studyId;
    private String Box;
    private String estado;
    private Integer activeSearch;

    public String getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(String specimenId) {
        this.specimenId = specimenId;
    }

    public void setAliquotId(String aliquotId) {
        this.aliquotId = aliquotId;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getOrthocode() {
        return orthocode;
    }

    public void setOrthocode(String orthocode) {
        this.orthocode = orthocode;
    }

    public String getBox() {
        return Box;
    }

    public void setBox(String box) {
        Box = box;
    }

    public String getLabReceiptDate() {
        return labReceiptDate;
    }

    public void setLabReceiptDate(String labReceiptDate) {
        this.labReceiptDate = labReceiptDate;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }
}
