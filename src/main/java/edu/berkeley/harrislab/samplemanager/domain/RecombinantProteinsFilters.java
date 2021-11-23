package edu.berkeley.harrislab.samplemanager.domain;
import java.util.Date;

public class RecombinantProteinsFilters {

    private String protrecombinante_id;
    private String protein_name;
    private String protein_origin;
    private String Construct_name;
    private Date date_transfection;

    private Integer activeSearch;

    public String getProtrecombinante_id() {
        return protrecombinante_id;
    }

    public void setProtrecombinante_id(String protrecombinante_id) {
        this.protrecombinante_id = protrecombinante_id;
    }

    public String getProtein_name() {
        return protein_name;
    }

    public void setProtein_name(String protein_name) {
        this.protein_name = protein_name;
    }

    public String getProtein_origin() {
        return protein_origin;
    }

    public void setProtein_origin(String protein_origin) {
        this.protein_origin = protein_origin;
    }

    public String getConstruct_name() {
        return Construct_name;
    }

    public void setConstruct_name(String Construct_name) {
        this.Construct_name = Construct_name;
    }

    public Date getDate_transfection() {
        return date_transfection;
    }

    public void setDate_transfection(Date date_transfection) {
        this.date_transfection = date_transfection;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }

}
