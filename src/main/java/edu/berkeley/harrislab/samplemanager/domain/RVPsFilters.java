package edu.berkeley.harrislab.samplemanager.domain;

public class RVPsFilters {

    private String rvpId;
    private Integer num_aliq;
    private String virusSerotype;
    private String batchName;

    private Integer activeSearch;

    public String getRvpId() {
        return rvpId;
    }

    public void setRvpId(String rvpId) {
        this.rvpId = rvpId;
    }

    public Integer getNum_aliq() {
        return num_aliq;
    }

    public void setNum_aliq(Integer num_aliq) {
        this.num_aliq = num_aliq;
    }

    public String getVirusSerotype() {
        return virusSerotype;
    }

    public void setVirusSerotype(String virusSerotype) {
        this.virusSerotype = virusSerotype;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }

}
