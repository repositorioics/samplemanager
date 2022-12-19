package edu.berkeley.harrislab.samplemanager.domain;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "molecularclone", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "specimenId"))
public class MolecularClone extends BaseMetaData implements Auditable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */

	private String systemId;
	private String specimenId;
	private Integer passage_number;
	private Date date_s;
    //General info
	private String specimen_condition;  //Non_concentrated, Concentrated, Purified (selection 1 option)
	private String specimen_type ;  //Clinical_Isolate, Infectious_Molecular_Clone_(Wild Type, WT),Mutant (IMC),Chimera (IMC) (selection 1 option)
	private String cell_line;
	private String experiment_id;
	private String researcher_name;

    //Specimen Information ( !! This part is dependent to the “Specimen type” answer)
   // If “Clinical Isolate” or “Infectious Molecular Clone (WT)”

    private String genus ; //Flavivirus, Alphavirus, Coronavirus, Influenza virus, Adenovirus, Lentivirus, Other (selection 1 option)
	private String organism_name;
    private String strain_name ;
    private String isolation_country;
    private String collection_date;

    //If “Mutant (IMC)

    private String genus_imc ; //Flavivirus, Alphavirus, Coronavirus, Influenza virus, Adenovirus, Lentivirus, Other (selection 1 option)
    private String organism_name_imc;
    private String strain_name_imc ;
    private String isolation_country_imc;
    private String collection_date_imc;
    private String mutation_type_imc; // Substitution, Insertion, Deletion  (selection 1 option)
    private String region ;
    private String note;

    //If “Chimera (IMC)”
        //Primary sequence
    private String genus_ch_imc ; //Flavivirus, Alphavirus, Coronavirus, Influenza virus, Adenovirus, Lentivirus, Other (selection 1 option)
    private String organism_name_ch_imc;
    private String strain_name_ch_imc ;
    private String region_ch_imc ;

       //Secondary sequence
    private String genus_ch_imc_2 ; //Flavivirus, Alphavirus, Coronavirus, Influenza virus, Adenovirus, Lentivirus, Other (selection 1 option)
    private String organism_name_ch_imc_2;
    private String strain_name_ch_imc_2 ;
    private String region_name_ch_imc_2 ;

    //Additional Mutation

    private String additional_mutation; // YES (Mutation Type), NO
    private String mutation_type_additional_mutation;     //Substitution, Insertion, Deletion
    private String region_additional_mutation ;
    private String note_additional_mutation;

      //Quantification / Titration   ( infectious_unit, protein_quantification, other )
    private String infectious_unit; // NO, YES (Viral Titer, Unit (FFU/mL, PFU/mL), Cell line, Date)
    private String viral_titer;
    private String unit_qt; // FFU/mL, PFU/mL)
    private String cell_line_qt;
    private Date date_qt;

    //Protein quantification
    private String protein_quantification; //(NO, YES (Concentration, Unit(mg/mL, µg/mL, ng/mL), Technique, Date)
    private Float concentration;
    private String unit_pq; //  (mg/mL, µg/mL, ng/mL    )   (selection 1 option)
    private String technique_pq;
    private Date date_pq;

    //Other qt
    private String otherqt;  //NO, YES (Technique, Value, Unit, Date)
    private String technique_ot;
    private Float value;
    private String unit_ot;
    private Date date_pq1;

    //Quality Control Status
    private String virus_cross_contamination; //Not performed, Yes (Technique ,Specimen tested, Date, Note)
    private String technique_qcs;
    private String specimen_tested;
    private Date date_qcs;
    private String note_qcs;

    //Mycoplasma
    private String mycoplasma; //Not performed, Yes (Technique, Date, Note)
    private String technique_mc;
    private Date date_mc;
    private String note_mc;

    //Antigenic Integrity
    private String antigenic_integrity; //Not performed, Yes (Technique ,Specimen tested, Date, Note)
    private String technique_ai;
    private String specimen_tested_ai;
    private Date date_ai;
    private String note_ai;

   //Other  Quality Control Status
   private String other_antigenic_integrity; //No, Yes (Technique ,Specimen tested, Date, Note)
    private String technique_ai_other;
    private String specimen_tested_ai_other;
    private Date date_ai_other;
    private String note_ai_other;

    //Storage
    private Integer aliquot_volume;   //(Integer 1 – 2 000)   (µL)
    private String buffer;
    private String vial_id;
    private String cap_color;
    private String equipment;
    private String rack_name;
    private String box_name;



	public MolecularClone() {
		super();
	}
    public MolecularClone(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	
	@Column(name = "specimenId", nullable = false, length = 50)
	public String getSpecimenId() {
		return specimenId;
	}
	public void setSpecimenId(String specimenId) {
		this.specimenId = specimenId;
	}

    @Column(name = "passage_number", nullable = false, length = 50)
	public Integer getPassage_number() {
		return passage_number;
	}
	public void setPassage_number(Integer passage_number) {
		this.passage_number = passage_number;
	}

    @Column(name = "date_s", nullable = false, length = 50)
	public Date getDate_s() {
		return date_s;
	}
	public void setDate_s(Date date_s) {
		this.date_s = date_s;
	}

    @Column(name = "specimen_condition", nullable = false, length = 30)
	public String getSpecimen_condition() {
		return specimen_condition;
	}
	public void setSpecimen_condition(String specimen_condition) {
		this.specimen_condition = specimen_condition;
	}

    @Column(name = "specimen_type", nullable = false, length = 40)
	public String getSpecimen_type() {
		return specimen_type;
	}
	public void setSpecimen_type(String specimen_type) {
		this.specimen_type = specimen_type;
	}

	@Column(name = "cell_line", nullable = false, length = 20)
	public String getCell_line() {
		return cell_line;
	}
	public void setCell_line(String cell_line) {
		this.cell_line = cell_line;
	}

	@Column(name = "experiment_id", nullable = true)
	public String getExperiment_id() {
		return experiment_id;
	}
	public void setExperiment_id(String experiment_id) {
		this.experiment_id = experiment_id;
	}

	@Column(name = "researcher_name", nullable = false, length = 100)
	public String getResearcher_name() {
		return researcher_name;
	}
	public void setResearcher_name(String researcher_name) {
		this.researcher_name = researcher_name;
	}

	@Column(name = "genus", nullable = false, length = 20)
	public String getGenus() {
		return genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}

	@Column(name = "organism_name", nullable = false, length = 40)
	public String getOrganism_name() {
		return organism_name;
	}
	public void setOrganism_name(String organism_name) {
		this.organism_name = organism_name;
	}

    @Column(name = "strain_name", nullable = false, length =20)
    public String getStrain_name () {
        return strain_name;
    }
    public void setStrain_name (String strain_name) {
        this.strain_name = strain_name;
    }

    @Column(name = "isolation_country", nullable = false)
    public String getIsolation_country () {
        return isolation_country;
    }
    public void setIsolation_country (String isolation_country) {
        this.isolation_country = isolation_country;
    }

    @Column(name = "collection_date", nullable = true, length =40)
    public String getCollection_date () {
        return collection_date;
    }
    public void setCollection_date (String collection_date) {
        this.collection_date = collection_date;
    }

    @Column(name = "genus_imc", nullable = false, length =40)
    public String getGenus_imc () {
        return genus_imc;
    }
    public void setGenus_imc (String genus_imc) {
        this.genus_imc = genus_imc;
    }

    @Column(name = "organism_name_imc", nullable = false, length =10)
    public String getOrganism_name_imc () {
        return organism_name_imc;
    }
    public void setOrganism_name_imc (String organism_name_imc) {
        this.organism_name_imc = organism_name_imc;
    }

    @Column(name = "strain_name_imc", nullable = false, length =10)
    public String getStrain_name_imc () {
        return strain_name_imc;
    }
    public void setStrain_name_imc (String strain_name_imc) {
        this.strain_name_imc = strain_name_imc;
    }

    @Column(name = "isolation_country_imc", nullable = false, length =10)
    public String getIsolation_country_imc () {
        return isolation_country_imc;
    }
    public void setIsolation_country_imc (String isolation_country_imc) {
        this.isolation_country_imc = isolation_country_imc;
    }

    @Column(name = "collection_date_imc", nullable = false, length =40)
    public String getCollection_date_imc () {
        return collection_date_imc;
    }
    public void setCollection_date_imc (String collection_date_imc) {
        this.collection_date_imc = collection_date_imc;
    }



    @Column(name = "region", nullable = false, length =40)
    public String getRegion () {
        return region;
    }
    public void setRegion (String region) {
        this.region = region;
    }

    @Column(name = "note", nullable = false, length =40)
    public String getNote () {
        return note;
    }
    public void setNote (String note) {
        this.note = note;
    }

    @Column(name = "genus_ch_imc", nullable = false, length =40)
    public String getGenus_ch_imc () {
        return genus_ch_imc;
    }
    public void setGenus_ch_imc (String genus_ch_imc) {
        this.genus_ch_imc = genus_ch_imc;
    }

    @Column(name = "organism_name_ch_imc", nullable = false, length =40)
    public String getOrganism_name_ch_imc () {
        return organism_name_ch_imc;
    }
    public void setOrganism_name_ch_imc (String organism_name_ch_imc) {
        this.organism_name_ch_imc = organism_name_ch_imc;
    }

    @Column(name = "strain_name_ch_imc", nullable = false, length =40)
    public String getStrain_name_ch_imc () {
        return strain_name_ch_imc;
    }
    public void setStrain_name_ch_imc (String strain_name_ch_imc) {
        this.strain_name_ch_imc = strain_name_ch_imc;
    }


    @Column(name = "region_ch_imc", nullable = false, length =40)
    public String getRegion_ch_imc () {
        return region_ch_imc;
    }
    public void setRegion_ch_imc (String region_ch_imc) {
        this.region_ch_imc = region_ch_imc;
    }

    @Column(name = "genus_ch_imc_2", nullable = false, length =40)
    public String getGenus_ch_imc_2 () {
        return genus_ch_imc_2;
    }
    public void setGenus_ch_imc_2 (String genus_ch_imc_2) {
        this.genus_ch_imc_2 = genus_ch_imc_2;
    }

    @Column(name = "organism_name_ch_imc_2", nullable = false, length =40)
    public String getOrganism_name_ch_imc_2 () {
        return organism_name_ch_imc_2;
    }
    public void setOrganism_name_ch_imc_2 (String organism_name_ch_imc_2) {
        this.organism_name_ch_imc_2 = organism_name_ch_imc_2;
    }

    @Column(name = "strain_name_ch_imc_2", nullable = false, length =40)
    public String getStrain_name_ch_imc_2 () {
        return strain_name_ch_imc_2;
    }
    public void setStrain_name_ch_imc_2 (String strain_name_ch_imc_2) {
        this.strain_name_ch_imc_2 = strain_name_ch_imc_2;
    }

    @Column(name = "region_name_ch_imc_2", nullable = false, length =40)
    public String getRegion_name_ch_imc_2 () {
        return region_name_ch_imc_2;
    }
    public void setRegion_name_ch_imc_2 (String region_name_ch_imc_2) {
            this.region_name_ch_imc_2 = region_name_ch_imc_2;
    }

    @Column(name = "additional_mutation", nullable = true, length =40)
    public String getAdditional_mutation () {
        return additional_mutation;
    }
    public void setAdditional_mutation (String additional_mutation) {
        this.additional_mutation = additional_mutation;
    }

    @Column(name = "mutation_type_additional_mutation", nullable = true, length =40)
    public String getMutation_type_additional_mutation () {
        return mutation_type_additional_mutation;
    }
    public void setMutation_type_additional_mutation (String mutation_type_additional_mutation) {
        this.mutation_type_additional_mutation = mutation_type_additional_mutation;
    }

    @Column(name = "region_additional_mutation", nullable = true, length =40)
    public String getRegion_additional_mutation () {
        return region_additional_mutation;
    }
    public void setRegion_additional_mutation (String region_additional_mutation) {
        this.region_additional_mutation = region_additional_mutation;
    }

    @Column(name = "note_additional_mutation", nullable = true, length =40)
    public String getNote_additional_mutation () {
        return note_additional_mutation;
    }
    public void setNote_additional_mutation (String note_additional_mutation) {
        this.note_additional_mutation = note_additional_mutation;
    }

    @Column(name = "infectious_unit; ", nullable = false, length =40)
    public String getInfectious_unit () {
        return infectious_unit ;
    }
    public void setInfectious_unit  (String infectious_unit ) {
        this.infectious_unit = infectious_unit ;
    }

    @Column(name = "viral_titer", nullable = false, length =40)
    public String getViral_titer () {
        return viral_titer;
    }
    public void setViral_titer (String viral_titer) {
        this.viral_titer = viral_titer;
    }

    @Column(name = "unit_qt", nullable = false, length =40)
    public String getUnit_qt () {
        return unit_qt;
    }
    public void setUnit_qt (String unit_qt) {
        this.unit_qt = unit_qt;
    }

    @Column(name = "cell_line_qt", nullable = false, length =40)
    public String getCell_line_qt () {
        return cell_line_qt;
    }
    public void setCell_line_qt (String cell_line) {
        this.cell_line_qt = cell_line_qt;
    }

    @Column(name = "date_qt", nullable = false)
    public Date getDate_qt () {
        return date_qt;
    }
    public void setDate_qt (Date date_qt) {
        this.date_qt = date_qt;
    }

    @Column(name = "protein_quantification", nullable = false, length =40)
    public String getProtein_quantification () {
        return protein_quantification;
    }
    public void setProtein_quantification (String protein_quantification) {
        this.protein_quantification = protein_quantification;
    }

    @Column(name = "concentration", nullable = false)
    public Float  getConcentration () {
        return concentration;
    }
    public void setConcentration (Float  concentration) {
        this.concentration = concentration;
    }

    @Column(name = "unit_pq", nullable = true, length =40)
    public String getUnit_pq () {
        return unit_pq;
    }
    public void setUnit_pq (String unit_pq) {
        this.unit_pq = unit_pq;
    }

    @Column(name = "technique_pq", nullable = false, length =40)
    public String getTechnique_pq () {
        return technique_pq;
    }
    public void setTechnique_pq (String technique_pq) {
        this.technique_pq = technique_pq;
    }

    @Column(name = "date_pq", nullable = false)
    public Date getDate_pq () {
        return date_pq;
    }
    public void setDate_pq (Date date_pq) {
        this.date_pq = date_pq;
    }

    @Column(name = "otherqt", nullable = false, length =40)
    public String getOtherqt () {
        return otherqt;
    }
    public void setOtherqt (String otherqt) {
        this.otherqt = otherqt;
    }

    @Column(name = "technique_ot", nullable = true, length =40)
    public String getTechnique_ot () {
        return technique_ot;
    }
    public void setTechnique_ot (String technique_ot) {
        this.technique_ot = technique_ot;
    }

    @Column(name = "value", nullable = true)
    public Float getValue () {
        return value;
    }
    public void setValue (Float value) {
        this.value = value;
    }

    @Column(name = "unit_ot", nullable = true, length =40)
    public String getUnit_ot () {
        return unit_ot;
    }
    public void setUnit_ot (String unit_ot) {
        this.unit_ot = unit_ot;
    }

    @Column(name = "date_pq1", nullable = true, length =40)
    public Date getDate_pq1 () {
        return date_pq1;
    }
    public void setDate_pq1 (Date date_pq1) {
        this.date_pq1 = date_pq1;
    }

    @Column(name = "virus_cross_contamination ", nullable = false, length =40)
    public String getVirus_cross_contamination () {
        return virus_cross_contamination;
    }
    public void setVirus_cross_contamination  (String virus_cross_contamination) {
        this.virus_cross_contamination = virus_cross_contamination;
    }
    @Column(name = "technique_qcs", nullable = false, length =40)
    public String getTechnique_qcs () {
        return technique_qcs;
    }
    public void setTechnique_qcs (String technique_qcs) {
        this.technique_qcs = technique_qcs;
    }

    @Column(name = "specimen_tested", nullable = false, length =40)
    public String getSpecimen_tested () {
        return specimen_tested;
    }
    public void setSpecimen_tested (String specimen_tested) {
        this.specimen_tested = specimen_tested;;
    }
    @Column(name = "date_qcs", nullable = false)
    public Date getDate_qcs () {
        return date_qcs;
    }
    public void setDate_qcs (Date date_qcs) {
        this.date_qcs = date_qcs;
    }

    @Column(name = "note_qcs", nullable = false, length =40)
    public String getNote_qcs() {
        return note_qcs;
    }
    public void setNote_qcs (String note_qcs) {
        this.note_qcs = note_qcs;
    }

    @Column(name = "mycoplasma", nullable = false, length =40)
    public String getMycoplasma () {
        return mycoplasma;
    }
    public void setMycoplasma (String mycoplasma) {
        this.mycoplasma = mycoplasma;
    }

    @Column(name = "technique_mc", nullable = false, length =40)
    public String getTechnique_mc () {
        return technique_mc;
    }
    public void setTechnique_mc (String technique_mc) {
        this.technique_mc = technique_mc;
    }

    @Column(name = "date_mc", nullable = false)
    public Date getDate_mc () {
        return date_mc;
    }
    public void setDate_mc (Date date_mc) {
        this.date_mc = date_mc;
    }

    @Column(name = "note_mc", nullable = false, length =40)
    public String getNote_mc () {
        return note_mc;
    }
    public void setNote_mc (String note_mc) {
        this.note_mc = note_mc;
    }

    @Column(name = "antigenic_integrity", nullable = false, length =40)
    public String getAntigenic_integrity () {
        return antigenic_integrity;
    }
    public void setAntigenic_integrity (String antigenic_integrity) {
        this.antigenic_integrity = antigenic_integrity;
    }

    @Column(name = "technique_ai", nullable = false, length =40)
    public String getTechnique_ai () {
        return technique_ai;
    }
    public void setTechnique_ai (String technique_ai) {
        this.technique_ai = technique_ai;
    }

    @Column(name = "specimen_tested_ai", nullable = false, length =40)
    public String getSpecimen_tested_ai () {
        return specimen_tested_ai;
    }
    public void setSpecimen_tested_ai (String specimen_tested_ai) {
        this.specimen_tested_ai = specimen_tested_ai;
    }

    @Column(name = "date_ai", nullable = false)
    public Date getDate_ai () {
        return date_ai;
    }
    public void setDate_ai (Date date_ai) {
        this.date_ai = date_ai;
    }

    @Column(name = "note_ai", nullable = false, length =40)
    public String getNote_ai () {
        return note_ai;
    }
    public void setNote_ai (String note_ai) {
        this.note_ai = note_ai;
    }

    @Column(name = "other_antigenic_integrity", nullable = true, length =40)
    public String getOther_antigenic_integrity () {
        return other_antigenic_integrity;
    }
    public void setOther_antigenic_integrity (String other_antigenic_integrity) {
        this.other_antigenic_integrity = other_antigenic_integrity;
    }

    @Column(name = "technique_ai_other", nullable = true, length =40)
    public String getTechnique_ai_other () {
        return technique_ai_other;
    }
    public void setTechnique_ai_other (String technique_ai_other) {
        this.technique_ai_other = technique_ai_other;
    }

    @Column(name = "specimen_tested_ai_other", nullable = true, length =40)
    public String getSpecimen_tested_ai_other () {
        return specimen_tested_ai_other;
    }
    public void setSpecimen_tested_ai_other (String specimen_tested_ai_other) {
        this.specimen_tested_ai_other = specimen_tested_ai_other;
    }

    @Column(name = "date_ai_other", nullable = true)
    public Date getDate_ai_other () {
        return date_ai_other;
    }
    public void setDate_ai_other (Date date_ai_other) {
        this.date_ai_other = date_ai_other;
    }


    @Column(name = "note_ai_other", nullable = true, length =40)
    public String getNote_ai_other () {
        return note_ai_other;
    }
    public void setNote_ai_other (String note_ai_other) {
        this.note_ai_other = note_ai_other;
    }


@Column(name = "aliquot_volume", nullable = true)
public Integer getAliquot_volume () {
        return aliquot_volume;
        }
public void setAliquot_volume (Integer aliquot_volume) {
        this.aliquot_volume = aliquot_volume;
        }


@Column(name = "buffer", nullable = true, length =40)
public String getBuffer () {
        return buffer;
        }
public void setBuffer (String buffer) {
        this.buffer = buffer;
        }


@Column(name = "vial_id", nullable = true, length =40)
public String getVial_id () {
        return vial_id;
        }
public void setVial_id (String vial_id) {
        this.vial_id = vial_id;
        }


    @Column(name = "cap_color", nullable = true, length =40)
    public String getCap_color () {
        return cap_color;
    }
    public void setCap_color (String cap_color) {
        this.cap_color = cap_color;
    }



    @Column(name = "equipment", nullable = true, length =40)
    public String getEquipment() {
        return equipment;
    }
    public void setEquipment (String equipment) {
        this.equipment = equipment;
    }

    @Column(name = "rack_name", nullable = true, length =40)
    public String getRack_name  () {
        return rack_name;
    }
    public void setRack_name (String rack_name) {
        this.rack_name = rack_name;
    }

    @Column(name = "box_name", nullable = true, length =40)
    public String getBox_name () {
        return box_name;
    }
    public void setBox_name (String box_name) {
        this.box_name = box_name;
    }
	

	@Override
	public String toString(){
		return specimenId;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MolecularClone))
			return false;
		
		MolecularClone castOther = (MolecularClone) other;

		return (this.getSpecimenId().equals(castOther.getSpecimenId()));
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
