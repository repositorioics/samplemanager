package edu.berkeley.harrislab.samplemanager.domain;

public class PrimersFilters {

    private String primers_id;
    private Integer primer_number;
    private String primer_name;
    private String primer_sequence;

    private Integer activeSearch;

    public String getPrimers_id() {
        return primers_id;
    }

    public void setPrimers_id(String primers_id) {
        this.primers_id = primers_id;
    }

    public Integer getPrimer_number() {
        return primer_number;
    }

    public void setPrimer_number(Integer primer_number) {
        this.primer_number = primer_number;
    }

    public String getPrimer_name() {
        return primer_name;
    }

    public void setPrimer_name(String primer_name) {
        this.primer_name = primer_name;
    }

    public String getPrimer_sequence() {
        return primer_sequence;
    }

    public void setPrimer_sequence(String primer_sequence) {
        this.primer_sequence = primer_sequence;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }

}
