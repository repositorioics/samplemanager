package edu.berkeley.harrislab.samplemanager.domain;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "viruses", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "virusId"))
public class Viruses extends BaseMetaData implements Auditable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */

	private String systemId;
	private String virusId;
	private String virus_Serotype;
	private String strain;
	private String batch_number;
	private String passage;
	private String experiment_id ;

	private String stage_of_production;
	private String amount_of_Unconc_virus;
	private Date date_Uncon_Collected;
	private Integer num_aliquots;
	private Float aliquot_volume;

    private String amount_of_Conc_virus;
    private Date date_of_Conc;
    private String amount_of_Purified;
    private Date date_of_Purification;
    private String qc_PCR_Check;
    private String qc_ELISA_Check;
    private String notes_on_QC;
    private String num_of_collections;
    private String day_Virus_Collected;

    private String cell_line_and_passage;
    private String viral_inoculum_vial_ID;
    private String bca_Concentration;
    private String fluorospot_check;
    private String notes_on_FS_check;
    private String viral_Titer;
    private String storage_temperature;


    private String loc_freezer;
    private String loc_rack;
    private String loc_box;
    private Integer loc_pos;

    private String comments;

	public Viruses() {
		super();
	}
    public Viruses(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	
	@Column(name = "virusId", nullable = false, length = 50)
	public String getVirusId() {
		return virusId;
	}
	public void setVirusId(String virusId) {
		this.virusId = virusId;
	}
	@Column(name = "virus_Serotype", nullable = false, length = 50)
	public String getVirus_Serotype() {
		return virus_Serotype;
	}
	public void setVirus_Serotype(String virus_Serotype) {
		this.virus_Serotype = virus_Serotype;
	}
	@Column(name = "strain", nullable = false, length = 50)
	public String getStrain() {
		return strain;
	}
	public void setStrain(String strain) {
		this.strain = strain;
	}
	@Column(name = "batch_number", nullable = false, length = 50)
	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}
	@Column(name = "passage", nullable = false)
	public String getPassage() {
		return passage;
	}
	public void setPassage(String passage) {
		this.passage = passage;
	}
	@Column(name = "experiment_id", nullable = true)
	public String getExperiment_id() {
		return experiment_id;
	}
	public void setExperiment_id(String experiment_id) {
		this.experiment_id = experiment_id;
	}

	@Column(name = "stage_of_production", nullable = true)
	public String getStage_of_production() {
		return stage_of_production;
	}
	public void setStage_of_production(String stage_of_production) {
		this.stage_of_production = stage_of_production;
	}

	@Column(name = "amount_of_Unconc_virus", nullable = true, length = 50)
	public String getAmount_of_Unconc_virus() {
		return amount_of_Unconc_virus;
	}
	public void setAmount_of_Unconc_virus(String amount_of_Unconc_virus) {
		this.amount_of_Unconc_virus = amount_of_Unconc_virus;
	}

	@Column(name = "date_Uncon_Collected", nullable = true, length = 50)
	public Date getDate_Uncon_Collected() {
		return date_Uncon_Collected;
	}
	public void setDate_Uncon_Collected(Date date_Uncon_Collected) {
		this.date_Uncon_Collected = date_Uncon_Collected;
	}

	@Column(name = "num_aliquots", nullable = true, length = 50)
	public Integer getNum_aliquots() {
		return num_aliquots;
	}
	public void setNum_aliquots(Integer num_aliquots) {
		this.num_aliquots = num_aliquots;
	}

	@Column(name = "aliquot_volume", nullable = true, length = 50)
	public Float getAliquot_volume() {
		return aliquot_volume;
	}
	public void setAliquot_volume(Float aliquot_volume) {
		this.aliquot_volume = aliquot_volume;
	}

    @Column(name = "amount_of_Conc_virus", nullable = true, length =40)
    public String getAmount_of_Conc_virus () {
        return amount_of_Conc_virus;
    }
    public void setAmount_of_Conc_virus (String amount_of_Conc_virus) {
        this.amount_of_Conc_virus = amount_of_Conc_virus;
    }

    @Column(name = "date_of_Conc", nullable = true, length =40)
    public Date getDate_of_Conc () {
        return date_of_Conc;
    }
    public void setDate_of_Conc (Date date_of_Conc) {
        this.date_of_Conc = date_of_Conc;
    }

    @Column(name = "amount_of_Purified", nullable = true, length =40)
    public String getAmount_of_Purified () {
        return amount_of_Purified;
    }
    public void setAmount_of_Purified (String amount_of_Purified) {
        this.amount_of_Purified = amount_of_Purified;
    }

    @Column(name = "date_of_Purification", nullable = true, length =40)
    public Date getDate_of_Purification () {
        return date_of_Purification;
    }
    public void setDate_of_Purification (Date date_of_Purification) {
        this.date_of_Purification = date_of_Purification;
    }

    @Column(name = "qc_PCR_Check", nullable = true, length =40)
    public String getQc_PCR_Check () {
        return qc_PCR_Check;
    }
    public void setQc_PCR_Check (String qc_PCR_Check) {
        this.qc_PCR_Check = qc_PCR_Check;
    }

    @Column(name = "qc_ELISA_Check", nullable = true, length =40)
    public String getQc_ELISA_Check () {
        return qc_ELISA_Check;
    }
    public void setQc_ELISA_Check (String qc_ELISA_Check) {
        this.qc_ELISA_Check = qc_ELISA_Check;
    }

    @Column(name = "notes_on_QC", nullable = true, length =40)
    public String getNotes_on_QC () {
        return notes_on_QC;
    }
    public void setNotes_on_QC (String notes_on_QC) {
        this.notes_on_QC = notes_on_QC;
    }

    @Column(name = "num_of_collections", nullable = true, length =40)
    public String getNum_of_collections () {
        return num_of_collections;
    }
    public void setNum_of_collections (String num_of_collections) {
        this.num_of_collections = num_of_collections;
    }

    @Column(name = "day_Virus_Collected", nullable = true, length =40)
    public String getDay_Virus_Collected () {
        return day_Virus_Collected;
    }
    public void setDay_Virus_Collected (String day_Virus_Collected) {
        this.day_Virus_Collected = day_Virus_Collected;
    }

    @Column(name = "cell_line_and_passage", nullable = true, length =40)
    public String getCell_line_and_passage () {
        return cell_line_and_passage;
    }
    public void setCell_line_and_passage (String cell_line_and_passage) {
        this.cell_line_and_passage = cell_line_and_passage;
    }

    @Column(name = "viral_inoculum_vial_ID", nullable = true, length =40)
    public String getViral_inoculum_vial_ID () {
        return viral_inoculum_vial_ID;
    }
    public void setViral_inoculum_vial_ID (String viral_inoculum_vial_ID) {
        this.viral_inoculum_vial_ID = viral_inoculum_vial_ID;
    }


    @Column(name = "bca_Concentration", nullable = true, length =40)
    public String getBca_Concentration () {
        return bca_Concentration;
    }
    public void setBca_Concentration (String bca_Concentration) {
        this.bca_Concentration = bca_Concentration;
    }


    @Column(name = "fluorospot_check", nullable = true, length =40)
    public String getFluorospot_check () {
        return fluorospot_check;
    }
    public void setFluorospot_check (String fluorospot_check) {
        this.fluorospot_check = fluorospot_check;
    }

    @Column(name = "notes_on_FS_check", nullable = true, length =40)
    public String getNotes_on_FS_check () {
        return notes_on_FS_check;
    }
    public void setNotes_on_FS_check (String notes_on_FS_check) {
        this.notes_on_FS_check = notes_on_FS_check;
    }

    @Column(name = "viral_Titer", nullable = true, length =40)
    public String getViral_Titer () {
        return viral_Titer;
    }
    public void setViral_Titer (String viral_Titer) {
        this.viral_Titer = viral_Titer;
    }
    @Column(name = "storage_temperature", nullable = true, length =40)
    public String getStorage_temperature () {
        return storage_temperature;
    }
    public void setStorage_temperature (String storage_temperature) {
        this.storage_temperature = storage_temperature;
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
		return virusId;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Viruses))
			return false;
		
		Viruses castOther = (Viruses) other;

		return (this.getVirusId().equals(castOther.getVirusId()));
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
