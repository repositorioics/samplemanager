package edu.berkeley.harrislab.samplemanager.domain;

public class AntibodysFilters {

    private String antibody_id;
    private String antibody_name;
    private Integer activeSearch;

    public String getantibody_id() {
        return antibody_id;
    }

    public void setantibody_id(String antibody_id) {
        this.antibody_id = antibody_id;
    }

    public String getantibody_name() {
        return antibody_name;
    }

    public void setantibody_name(String antibody_name) {
        this.antibody_name = antibody_name;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }

}
