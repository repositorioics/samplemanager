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
@Table(name = "antibodies", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "antibody_id"))
public class Antibody extends BaseMetaData implements Auditable {


  private static final long serialVersionUID = 1L;

    private String systemId;
    private String antibody_id;
    private String antibody_name;
    private String target_protein;
    private String target_domain;
    private String target_epitope;
    private String antibody_isotype;
    private String recognizes;
    private String batch_number;
    private Date date_produced;
    private String person_name;
    private String source_name;
    private String raised_in;
    private Float aliquot_volume;
    private String medium_buffer;
    private String concentration;
    private String technique1;
    private String technique1_concentration1;
    private String technique2;
    private String technique2_concentration2;
    private String technique3;
    private String technique3_concentration3;
    private String storage_temperature;
    private String freezer_name;

    private String rack_name;
    private String box_name;
    private Integer position_in_the_box;
    private String additional_comments;


    public Antibody() {
        super();
    }
    public Antibody(Date recordDate, String recordUser, String recordIp, char pasive) {
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

    @Column(name = "antibody_id", nullable = false, length = 50)
    public String getAntibody_id() {
        return antibody_id;
    }
    public void setAntibody_id (String antibody_id) {
        this.antibody_id = antibody_id;
    }

    @Column(name = "antibody_name", nullable = true, length =30)
    public String getAntibody_name () {
        return antibody_name;
    }
    public void setAntibody_name (String antibody_name) {
        this.antibody_name = antibody_name;
    }

    @Column(name = "target_protein", nullable = true, length =30)
    public String getTarget_protein  () {
        return target_protein;
    }
    public void setTarget_protein  (String target_protein) {
        this.target_protein = target_protein;
    }

    @Column(name = "target_domain", nullable = true, length =30)
    public String getTarget_domain () {
        return target_domain;
    }
    public void setTarget_domain (String target_domain) {
        this.target_domain = target_domain;
    }

    @Column(name = "target_epitope", nullable = true, length =30)
    public String getTarget_epitope () {
        return target_epitope;
    }
    public void setTarget_epitope (String target_epitope) {
        this.target_epitope = target_epitope;
    }

    @Column(name = "antibody_isotype", nullable = true, length =30)
    public String getAntibody_isotype() {
        return antibody_isotype;
    }
    public void setAntibody_isotype(String antibody_isotype) {
        this.antibody_isotype = antibody_isotype;
    }

    @Column(name = "recognizes", nullable = true, length =30)
    public String getRecognizes () {
        return recognizes;
    }
    public void setRecognizes (String recognizes) {
        this.recognizes = recognizes;
    }

    @Column(name = "batch_number", nullable = true, length =30)
    public String getBatch_number () {
        return batch_number;
    }
    public void setBatch_number (String batch_number) {
        this.batch_number = batch_number;
    }

    @Column(name = "date_produced", nullable = false)
    public Date getDate_produced () {
        return date_produced;
    }
    public void setDate_produced(Date date_produced) {
        this.date_produced = date_produced;
    }

    @Column(name = "person_name", nullable = true, length =30)
    public String getPerson_name () {
        return person_name;
    }
    public void setPerson_name (String person_name) {
        this.person_name = person_name;
    }

    @Column(name = "source_name", nullable = true, length =40)
    public String getSource_name() {
        return source_name;
    }
    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    @Column(name = "raised_in", nullable = true, length =40)
    public String getRaised_in () {
        return source_name;
    }
    public void setRaised_in(String raised_in) {
        this.raised_in = raised_in;
    }

    @Column(name = "aliquot_volume", nullable = true)
    public Float getAliquot_volume() {
        return aliquot_volume;
    }
    public void setAliquot_volume(Float aliquot_volume) {
        this.aliquot_volume = aliquot_volume;
    }

    @Column(name = "medium_buffer", nullable = true, length =40)
    public String getMedium_buffer() {
        return medium_buffer;
    }
    public void setMedium_buffer(String medium_buffer) {
        this.medium_buffer = medium_buffer;
    }

    @Column(name = "concentration", nullable = true, length =10)
    public String getConcentration() {
        return concentration;
    }
    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    @Column(name = "technique1", nullable = true, length =40)
    public String getTechnique1() {
        return technique1;
    }
    public void setTechnique1(String technique1) {
        this.technique1 = technique1;
    }

    @Column(name = "technique1_concentration1", nullable = true, length =10)
    public String getTechnique1_concentration1() {
        return technique1_concentration1;
    }
    public void setTechnique1_concentration1(String technique1_concentration1) {
        this.technique1_concentration1 = technique1_concentration1;
    }

    @Column(name = "technique2", nullable = true, length =40)
    public String getTechnique2() {
        return technique2;
    }
    public void setTechnique2(String technique2) {
        this.technique2 = technique2;
    }

    @Column(name = "technique2_concentration2", nullable = true, length =10)
    public String getTechnique2_concentration2() {
        return technique2_concentration2;
    }
    public void setTechnique2_concentration2(String technique2_concentration2) {
        this.technique2_concentration2 = technique2_concentration2;
    }

    @Column(name = "technique3", nullable = true, length =40)
    public String getTechnique3() {
        return technique3;
    }
    public void setTechnique3(String technique3) {
        this.technique3 = technique3;
    }

    @Column(name = "technique3_concentration3", nullable = true, length =10)
    public String getTechnique3_concentration3() {
        return technique3_concentration3;
    }
    public void setTechnique3_concentration3(String technique3_concentration3) {
        this.technique3_concentration3 = technique3_concentration3;
    }

    @Column(name = "storage_temperature", nullable = true, length =30)
    public String getStorage_temperature () {
        return storage_temperature;
    }
    public void setStorage_temperature (String storage_temperature) {
        this.storage_temperature = storage_temperature;
    }

    @Column(name = "freezer_name", nullable = true, length =40)
    public String getFreezer_name() {
        return freezer_name;
    }
    public void setFreezer_name(String freezer_name) {
        this.freezer_name = freezer_name;
    }

    @Column(name = "box_name", nullable = true, length =40)
    public String getBox_name () {
        return box_name;
    }
    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    @Column(name = "rack_name", nullable = true, length =40)
    public String getRack_name() {
        return rack_name;
    }
    public void setRack_name(String rack_name) {
        this.rack_name = rack_name;
    }

    @Column(name = "position_in_the_box", nullable = true)
    public Integer getPosition_in_the_box() {
        return position_in_the_box;
    }
    public void setPosition_in_the_box(Integer position_in_the_box) {
        this.position_in_the_box = position_in_the_box;
    }

    @Column(name = "additional_comments", nullable = true, length =40)
    public String getAdditional_comments() {
        return additional_comments;
    }
    public void setAdditional_comments(String additional_comments) {
        this.additional_comments = additional_comments;
    }
    @Override
    public String toString(){
        return antibody_name;
    }

    @Override
    public boolean equals(Object other) {

        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof Rack))
            return false;

        Antibody castOther = (Antibody) other;

        return (this.getAntibody_id().equals(castOther.getAntibody_id()));
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
