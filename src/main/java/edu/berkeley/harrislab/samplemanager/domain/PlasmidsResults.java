package edu.berkeley.harrislab.samplemanager.domain;

public class PlasmidsResults {

    private String systemId;
    private String plasmids_id;
    private String plasmid_name;
    private String sequencing_confirmed;
    private String sequencing_primers;
    private String backbone_vector;
    private String antibiotic_resistant;
    private String growth_conditions;
    private String midiprep_purified;   //Yes/No
    private String concentration;
    private String comments;
    private String glycerol_stock; //Yes/No
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

    public String getSequencing_confirmed() {
        return sequencing_confirmed;
    }

    public void setSequencing_confirmed(String sequencing_confirmed) {
        this.sequencing_confirmed = sequencing_confirmed;
    }

    public String getSequencing_primers() {
        return sequencing_primers;
    }

    public void setSequencing_primers(String sequencing_primers) {
        this.sequencing_primers = sequencing_primers;
    }

    public String getBackbone_vector() {
        return backbone_vector;
    }

    public void setBackbone_vector(String backbone_vector) {
        this.backbone_vector = backbone_vector;
    }

    public String getAntibiotic_resistant() {
        return antibiotic_resistant;
    }

    public void setAntibiotic_resistant(String antibiotic_resistant) {
        this.antibiotic_resistant = antibiotic_resistant;
    }

    public String getGrowth_conditions() {
        return growth_conditions;
    }

    public void setGrowth_conditions(String growth_conditions) {
        this.growth_conditions = growth_conditions;
    }

    public String getMidiprep_purified() {
        return midiprep_purified;
    }

    public void setMidiprep_purified(String midiprep_purified) {
        this.midiprep_purified = midiprep_purified;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getGlycerol_stock() {
        return glycerol_stock;
    }

    public void setGlycerol_stock(String glycerol_stock) {
        this.glycerol_stock = glycerol_stock;
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
