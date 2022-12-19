package edu.berkeley.harrislab.samplemanager.domain;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "cells", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "cellsId"))
public class Cells extends BaseMetaData implements Auditable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */

	private String systemId;
	private String cellsId;
	private String cell_line_name;
	private Integer passage_number;
	private Date date_frozen;
	private String operator_name;
	private String company_name ;

	private String catalog_code;
	private String type_of_culture;   //seleccion adherent , suspension
	private String original_cell_type;
	private String original_organ;
	private String organism_name;

    private String cell_density;
    private Float volume_mL;
    private String freezing_medium;
    private String culture_media;
    private String QC_status;   //seleccion YES, NO
    private String QC_test_name; // Virus (DENV,ZIKV, OTHER (Bacteria,Mycoplasma,Cell identity))
    private String genetic_modification; // YES (transfection,transduction), NO
    private String selection_reagent;
    private String selection_concentration;

    private String freezer_name;
    private String rack_name;
    private String box_name;
    private Integer position_in_the_box;


	public Cells() {
		super();
	}
    public Cells(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	
	@Column(name = "cellsId", nullable = false, length = 50)
	public String getCellsId() {
		return cellsId;
	}
	public void setCellsId(String cellsId) {
		this.cellsId = cellsId;
	}

    @Column(name = "cell_line_name", nullable = false, length = 40)
	public String getCell_line_name() {
		return cell_line_name;
	}
	public void setCell_line_name(String cell_line_name) {
		this.cell_line_name = cell_line_name;
	}

    @Column(name = "passage_number", nullable = false, length = 50)
	public Integer getPassage_number() {
		return passage_number;
	}
	public void setPassage_number(Integer passage_number) {
		this.passage_number = passage_number;
	}

    @Column(name = "date_frozen", nullable = false, length = 50)
	public Date getDate_frozen() {
		return date_frozen;
	}
	public void setDate_frozen(Date date_frozen) {
		this.date_frozen = date_frozen;
	}

    @Column(name = "operator_name", nullable = false, length = 30)
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

    @Column(name = "company_name", nullable = true, length = 40)
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	@Column(name = "catalog_code", nullable = true, length = 20)
	public String getCatalog_code() {
		return catalog_code;
	}
	public void setCatalog_code(String catalog_code) {
		this.catalog_code = catalog_code;
	}

	@Column(name = "type_of_culture", nullable = true)
	public String getType_of_culture() {
		return type_of_culture;
	}
	public void setType_of_culture(String type_of_culture) {
		this.type_of_culture = type_of_culture;
	}

	@Column(name = "original_cell_type", nullable = true, length = 100)
	public String getOriginal_cell_type() {
		return original_cell_type;
	}
	public void setOriginal_cell_type(String original_cell_type) {
		this.original_cell_type = original_cell_type;
	}

	@Column(name = "original_organ", nullable = true, length = 20)
	public String getOriginal_organ() {
		return original_organ;
	}
	public void setOriginal_organ(String original_organ) {
		this.original_organ = original_organ;
	}

	@Column(name = "organism_name", nullable = true, length = 40)
	public String getOrganism_name() {
		return organism_name;
	}
	public void setOrganism_name(String organism_name) {
		this.organism_name = organism_name;
	}

    @Column(name = "cell_density", nullable = true, length =20)
    public String getCell_density () {
        return cell_density;
    }
    public void setCell_density (String cell_density) {
        this.cell_density = cell_density;
    }

    @Column(name = "volume_mL", nullable = true)
    public Float getVolume_mL () {
        return volume_mL;
    }
    public void setVolume_mL (Float volume_mL) {
        this.volume_mL = volume_mL;
    }

    @Column(name = "freezing_medium", nullable = true, length =40)
    public String getFreezing_medium () {
        return freezing_medium;
    }
    public void setFreezing_medium (String freezing_medium) {
        this.freezing_medium = freezing_medium;
    }

    @Column(name = "culture_media", nullable = true, length =40)
    public String getCulture_media () {
        return culture_media;
    }
    public void setCulture_media (String culture_media) {
        this.culture_media = culture_media;
    }

    @Column(name = "QC_status", nullable = true, length =10)
    public String getQC_status () {
        return QC_status;
    }
    public void setQC_status (String QC_status) {
        this.QC_status = QC_status;
    }

    @Column(name = "QC_test_name", nullable = true, length =10)
    public String getQC_test_name () {
        return QC_test_name;
    }
    public void setQC_test_name (String QC_test_name) {
        this.QC_test_name = QC_test_name;
    }

    @Column(name = "genetic_modification", nullable = true, length =10)
    public String getGenetic_modification () {
        return genetic_modification;
    }
    public void setGenetic_modification (String genetic_modification) {
        this.genetic_modification = genetic_modification;
    }

    @Column(name = "selection_reagent", nullable = true, length =40)
    public String getSelection_reagent () {
        return selection_reagent;
    }
    public void setSelection_reagent (String selection_reagent) {
        this.selection_reagent = selection_reagent;
    }

    @Column(name = "selection_concentration", nullable = true, length =40)
    public String getSelection_concentration () {
        return selection_concentration;
    }
    public void setSelection_concentration (String selection_concentration) {
        this.selection_concentration = selection_concentration;
    }

    @Column(name = "freezer_name", nullable = true, length =40)
    public String getFreezer_name() {
        return freezer_name;
    }
    public void setFreezer_name (String freezer_name) {
        this.freezer_name = freezer_name;
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

    @Column(name = "position_in_the_box", nullable = true)
    public Integer getPosition_in_the_box () {
        return position_in_the_box;
    }
    public void setPosition_in_the_box (Integer position_in_the_box) {
        this.position_in_the_box = position_in_the_box;
    }
	

	@Override
	public String toString(){
		return cellsId;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Cells))
			return false;
		
		Cells castOther = (Cells) other;

		return (this.getCellsId().equals(castOther.getCellsId()));
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
