package edu.berkeley.harrislab.samplemanager.domain;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Everts Morales Reyes.
 */
@Entity
@Table(name = "plasmids", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "plasmids_id"))
public class Plasmids extends BaseMetaData implements Auditable {


  private static final long serialVersionUID = 1L;

    private String systemId;
    private String plasmids_id;
    private String plasmid_name;
    private String sequencing_confirmed;  //Yes/No
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



    public Plasmids() {
        super();
    }
    public Plasmids(Date recordDate, String recordUser, String recordIp, char pasive) {
        super(recordDate, recordUser, recordIp, pasive);
        // TODO Auto-generated constructor stub
    }

    @Id
    @Column(name = "systemId", nullable = false, length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Column(name = "plasmids_id", nullable = false, length = 50)
    public String getPlasmids_id () {
        return plasmids_id;
    }
    public void setPlasmids_id (String plasmids_id) {
        this.plasmids_id = plasmids_id;
    }

    @Column(name = "plasmid_name", nullable = true, length =30)
    public String getPlasmid_name () {
        return plasmid_name;
    }
    public void setPlasmid_name (String plasmid_name) {
        this.plasmid_name = plasmid_name;
    }

    @Column(name = "sequencing_confirmed", nullable = true, length =3)
    public String getSequencing_confirmed () {
        return sequencing_confirmed;
    }
    public void setSequencing_confirmed  (String sequencing_confirmed) {
        this.sequencing_confirmed = sequencing_confirmed;
    }

    @Column(name = "sequencing_primers", nullable = true, length =20)
    public String getSequencing_primers () {
        return sequencing_primers;
    }
    public void setSequencing_primers (String sequencing_primers) {
        this.sequencing_primers = sequencing_primers;
    }

    @Column(name = "backbone_vector", nullable = true,  length =30)
    public String getBackbone_vector () {
        return backbone_vector;
    }
    public void setBackbone_vector (String backbone_vector) {
        this.backbone_vector = backbone_vector;
    }

    @Column(name = "antibiotic_resistant", nullable = true,  length =30)
    public String getAntibiotic_resistant() {
        return antibiotic_resistant;
    }
    public void setAntibiotic_resistant(String antibiotic_resistant) {
        this.antibiotic_resistant = antibiotic_resistant;
    }

    @Column(name = "growth_conditions", nullable = true, length =100)
    public String getGrowth_conditions () {
        return growth_conditions;
    }
    public void setGrowth_conditions (String growth_conditions) {
        this.growth_conditions = growth_conditions;
    }

    @Column(name = "midiprep_purified", nullable = true, length =3)
    public String getMidiprep_purified () {
        return midiprep_purified;
    }
    public void setMidiprep_purified (String midiprep_purified) {
        this.midiprep_purified = midiprep_purified;
    }

    @Column(name = "concentration", nullable = false)
    public String getConcentration () {
        return concentration;
    }
    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    @Column(name = "comments", nullable = true, length =40)
    public String getComments () {
        return comments;
    }
    public void setComments (String comments) {
        this.comments = comments;
    }

    @Column(name = "glycerol_stock", nullable = true, length =3)   //if Yes then Location
    public String getGlycerol_stock () {
        return glycerol_stock;
    }
    public void setGlycerol_stock (String glycerol_stock) {
        this.glycerol_stock = glycerol_stock;
    }

    @Column(name = "loc_freezer", nullable = true, length =40)
    public String getLoc_freezer() {
        return loc_freezer;
    }
    public void setLoc_freezer (String loc_freezer) {
        this.loc_freezer = loc_freezer;
    }

    @Column(name = "loc_rack", nullable = true, length =40)
    public String getLoc_rack  () {
        return loc_rack;
    }
    public void setLoc_rack (String loc_rack) {
        this.loc_rack = loc_rack;
    }

    @Column(name = "loc_box", nullable = true, length =40)
    public String getLoc_box () {
        return loc_box;
    }
    public void setLoc_box (String loc_box) {
        this.loc_box = loc_box;
    }

    @Column(name = "loc_pos", nullable = true)
    public Integer getLoc_pos () {
        return loc_pos;
    }
    public void setLoc_pos (Integer loc_pos) {
        this.loc_pos = loc_pos;
    }


    @Override
    public String toString(){
        return plasmid_name;
    }

    @Override
    public boolean equals(Object other) {

        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof Rack))
            return false;

        Plasmids castOther = (Plasmids) other;

        return (this.getPlasmids_id().equals(castOther.getPlasmids_id()));
    }
    @Override
    public boolean isFieldAuditable(String fieldname) {
        //Campos no auditables en la tabla
        if(fieldname.matches("recordDate")||fieldname.matches("recordUser")){
            return false;
        }
        return true;
    }


}
