package edu.berkeley.harrislab.samplemanager.domain;

public class VirusessFilters {

    private String virusId;
    private String experiment_id;
    private String virus_Serotype;
    private Float aliquot_volume;
    private Integer num_aliquots;

    private Integer activeSearch;

    public String getVirusId() {
        return virusId;
    }

    public void setVirusId(String virusId) {
        this.virusId = virusId;
    }

    public String getExperiment_id() {
        return experiment_id;
    }

    public void setExperiment_id(String experiment_id) {
        this.experiment_id = experiment_id;
    }

    public String getVirus_Serotype() {
        return virus_Serotype;
    }

    public void setVirus_Serotype(String virus_Serotype) {
        this.virus_Serotype = virus_Serotype;
    }

    public Float getAliquot_volume() {
        return aliquot_volume;
    }

    public void setAliquot_volume(Float aliquot_volume) {
        this.aliquot_volume = aliquot_volume;
    }

    public Integer getNum_aliquots() {
        return num_aliquots;
    }

    public void setNum_aliquots(Integer num_aliquots) {
        this.num_aliquots = num_aliquots;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }

}
