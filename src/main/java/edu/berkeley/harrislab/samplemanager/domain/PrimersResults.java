package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

public class PrimersResults {

    private String systemId;
    private String primers_id;
    private Integer primer_number;
    private String position_in_sequence;
    private String date_received;
    private String primer_name;
    private String primer_description;
    private String primer_sequence;
    private Integer primer_lenght;
    private String primer_designer;
    private String comments;
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

    public String getPosition_in_sequence() {
        return position_in_sequence;
    }

    public void setPosition_in_sequence(String position_in_sequence) {
        this.position_in_sequence = position_in_sequence;
    }

    public String getDate_received() {
        return date_received;
    }

    public void setDate_received(String date_received) {
        this.date_received = date_received;
    }

    public String getPrimer_name() {
        return primer_name;
    }

    public void setPrimer_name(String primer_name) {
        this.primer_name = primer_name;
    }

    public String getPrimer_description() {
        return primer_description;
    }

    public void setPrimer_description(String primer_description) {
        this.primer_description = primer_description;
    }

    public String getPrimer_sequence() {
        return primer_sequence;
    }

    public void setPrimer_sequence(String primer_sequence) {
        this.primer_sequence = primer_sequence;
    }

    public Integer getPrimer_lenght() {
        return primer_lenght;
    }

    public void setPrimer_lenght(Integer primer_lenght) {
        this.primer_lenght = primer_lenght;
    }

    public String getPrimer_designer() {
        return primer_designer;
    }

    public void setPrimer_designer(String primer_designer) {
        this.primer_designer = primer_designer;
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
