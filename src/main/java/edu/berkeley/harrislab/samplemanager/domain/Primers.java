package edu.berkeley.harrislab.samplemanager.domain;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Everts Morales Reyes.
 */
@Entity
@Table(name = "primers", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "primers_id"))
public class Primers extends BaseMetaData implements Auditable {


  private static final long serialVersionUID = 1L;

    private String systemId;
    private String primers_id;
    private Integer primer_number;
    private String position_in_sequence;
    private Date date_received;
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



    public Primers() {
        super();
    }
    public Primers(Date recordDate, String recordUser, String recordIp, char pasive) {
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

    @Column(name = "primers_id", nullable = false, length = 50)
    public String getPrimers_id () {
        return primers_id;
    }
    public void setPrimers_id (String primers_id) {
        this.primers_id = primers_id;
    }

    @Column(name = "primer_number", nullable = true, length =4)
    public Integer getPrimer_number () {
        return primer_number;
    }
    public void setPrimer_number (Integer primer_number) {
        this.primer_number = primer_number;
    }

    @Column(name = "position_in_sequence", nullable = true, length =3)
    public String getPosition_in_sequence () {
        return position_in_sequence;
    }
    public void setPosition_in_sequence  (String position_in_sequence) {
        this.position_in_sequence = position_in_sequence;
    }

    @Column(name = "date_received", nullable = true, length =20)
    public Date getDate_received () {
        return date_received;
    }
    public void setDate_received (Date date_received) {
        this.date_received = date_received;
    }

    @Column(name = "primer_name", nullable = true,  length =30)
    public String getPrimer_name () {
        return primer_name;
    }
    public void setPrimer_name (String primer_name) {
        this.primer_name = primer_name;
    }

    @Column(name = "primer_description", nullable = true,  length =30)
    public String getPrimer_description() {
        return primer_description;
    }
    public void setPrimer_description(String primer_description) {
        this.primer_description = primer_description;
    }

    @Column(name = "primer_sequence", nullable = true, length =50)
    public String getPrimer_sequence () {
        return primer_sequence;
    }
    public void setPrimer_sequence (String primer_sequence) {
        this.primer_sequence = primer_sequence;
    }

    @Column(name = "primer_lenght", nullable = true)
    public Integer getPrimer_lenght () {
        return primer_lenght;
    }
    public void setPrimer_lenght (Integer primer_lenght) {
        this.primer_lenght = primer_lenght;
    }

    @Column(name = "primer_designer", nullable = false, length =25)
    public String getPrimer_designer () {
        return primer_designer;
    }
    public void setPrimer_designer(String primer_designer) {
        this.primer_designer = primer_designer;
    }

    @Column(name = "comments", nullable = true, length =40)
    public String getComments () {
        return comments;
    }
    public void setComments (String comments) {
        this.comments = comments;
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
        return primer_name;
    }

    @Override
    public boolean equals(Object other) {

        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof Rack))
            return false;

        Primers castOther = (Primers) other;

        return (this.getPrimers_id().equals(castOther.getPrimers_id()));
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
