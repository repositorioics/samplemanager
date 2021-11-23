package edu.berkeley.harrislab.samplemanager.domain;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Everts Morales Reyes.
 */
@Entity
@Table(name = "recombinantproteins", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "protrecombinante_id"))
public class RecombinantProtein extends BaseMetaData implements Auditable {


  private static final long serialVersionUID = 1L;

    private String systemId;
    private String protrecombinante_id;
    private String protein_name;
    private String protein_origin;
    private String Construct_name;
    private Date date_transfection;
    private Integer vol_sn;
    private String pur_method;
    private String frac_retained;
    private Float vol_usable;
    private String dialysis_buffer;
    private Date date_purification;
    private String num_aliquot;
    private Float vol_aliquot;
    private Float conc_protein;
    private String loc_freezer;
    private String loc_rack;
    private String loc_box;
    private Integer loc_pos;
    private String comments;


    public RecombinantProtein() {
        super();
    }
    public RecombinantProtein(Date recordDate, String recordUser, String recordIp, char pasive) {
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

    @Column(name = "protrecombinante_id", nullable = false, length = 50)
    public String getProtrecombinante_id() {
        return protrecombinante_id;
    }
    public void setProtrecombinante_id (String protrecombinante_id) {
        this.protrecombinante_id = protrecombinante_id;
    }

    @Column(name = "protein_name", nullable = true, length =20)
    public String getProtein_name () {
        return protein_name;
    }
    public void setProtein_name (String protein_name) {
        this.protein_name = protein_name;
    }

    @Column(name = "protein_origin", nullable = true, length =12)
    public String getProtein_origin () {
        return protein_origin;
    }
    public void setProtein_origin  (String protein_origin) {
        this.protein_origin = protein_origin;
    }

    @Column(name = "Construct_name", nullable = true, length =10)
    public String getConstruct_name () {
        return Construct_name;
    }
    public void setConstruct_name (String Construct_name) {
        this.Construct_name = Construct_name;
    }

    @Column(name = "date_transfection", nullable = true)
    public Date getDate_transfection () {
        return date_transfection;
    }
    public void setDate_transfection (Date date_transfection) {
        this.date_transfection = date_transfection;
    }

    @Column(name = "vol_sn", nullable = true)
    public Integer getVol_sn() {
        return vol_sn;
    }
    public void setVol_sn(Integer vol_sn) {
        this.vol_sn = vol_sn;
    }

    @Column(name = "pur_method", nullable = true, length =40)
    public String getPur_method () {
        return pur_method;
    }
    public void setPur_method (String pur_method) {
        this.pur_method = pur_method;
    }

    @Column(name = "frac_retained", nullable = true, length =10)
    public String getFrac_retained () {
        return frac_retained;
    }
    public void setFrac_retained (String frac_retained) {
        this.frac_retained = frac_retained;
    }

    @Column(name = "vol_usable", nullable = false)
    public Float getVol_usable () {
        return vol_usable;
    }
    public void setVol_usable(Float vol_usable) {
        this.vol_usable = vol_usable;
    }

    @Column(name = "dialysis_buffer", nullable = true, length =30)
    public String getDialysis_buffer () {
        return dialysis_buffer;
    }
    public void setDialysis_buffer (String dialysis_buffer) {
        this.dialysis_buffer = dialysis_buffer;
    }

    @Column(name = "date_purification", nullable = true)
    public Date getDate_purification() {
        return date_purification;
    }
    public void setDate_purification(Date date_purification) {
        this.date_purification = date_purification;
    }

    @Column(name = "num_aliquot", nullable = true)
    public String getNum_aliquot() {
        return num_aliquot;
    }
    public void setNum_aliquot(String num_aliquot) {
        this.num_aliquot = num_aliquot;
    }

    @Column(name = "vol_aliquot", nullable = true)
    public Float getVol_aliquot () {
        return vol_aliquot;
    }
    public void setVol_aliquot (Float vol_aliquot) {
        this.vol_aliquot = vol_aliquot;
    }

    @Column(name = "conc_protein", nullable = true)
    public Float getConc_protein () {
        return conc_protein;
    }
    public void setConc_protein (Float conc_protein) {
        this.conc_protein = conc_protein;
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

    @Column(name = "comments", nullable = true, length =40)
    public String getComments () {
        return comments;
    }
    public void setComments (String comments) {
        this.comments = comments;
    }
    @Override
    public String toString(){
        return protein_name;
    }

    @Override
    public boolean equals(Object other) {

        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof Rack))
            return false;

        RecombinantProtein castOther = (RecombinantProtein) other;

        return (this.getProtrecombinante_id().equals(castOther.getProtrecombinante_id()));
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
