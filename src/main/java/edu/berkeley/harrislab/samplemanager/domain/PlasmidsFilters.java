package edu.berkeley.harrislab.samplemanager.domain;
import java.util.Date;

public class PlasmidsFilters {

    private String plasmids_id;
    private String plasmid_name;
    private String sequencing_primers;

    private Integer activeSearch;

    public String getPlasmids_id() {
        return plasmids_id;
    }

    public void setPlasmids_id(String plasmids_id) {
        this.plasmids_id = plasmids_id;
    }

    public String getPlasmid_name() {
        return plasmid_name;
    }

    public void setPlasmid_name(String plasmid_name) {
        this.plasmid_name = plasmid_name;
    }

    public String getSequencing_primers() {
        return sequencing_primers;
    }

    public void setSequencing_primers(String sequencing_primers) {
        this.sequencing_primers = sequencing_primers;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }

}
