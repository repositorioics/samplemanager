package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;



@Entity
@Table(name = "rvps", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "rvpId"))
public class RVP extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String systemId;
	private String rvpId;
	private String batchName;
	private String virusSerotype;
	private String provider;
	private Date date_exp;
	private Float aliquot_volume;

	private Integer num_aliq;
	private String working_dilution;
	private String infection_percentage;
	private String cellType;
	private String Time_hpi;

    private String comments;
    private String loc_freezer;
    private String loc_rack;
    private String loc_box;
    private Integer loc_pos;
	
	public RVP() {
		super();
	}
    public RVP(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	
	@Column(name = "rvpId", nullable = false, length = 50)
	public String getRvpId() {
		return rvpId;
	}
	public void setRvpId(String rvpId) {
		this.rvpId = rvpId;
	}
	@Column(name = "batchName", nullable = false, length = 50)
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	@Column(name = "virusSerotype", nullable = false, length = 50)
	public String getVirusSerotype() {
		return virusSerotype;
	}
	public void setVirusSerotype(String virusSerotype) {
		this.virusSerotype = virusSerotype;
	}
	@Column(name = "provider", nullable = false, length = 50)
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	@Column(name = "date_exp", nullable = false)
	public Date getDate_exp() {
		return date_exp;
	}
	public void setDate_exp(Date date_exp) {
		this.date_exp = date_exp;
	}
	@Column(name = "aliquot_volume", nullable = true)
	public Float getAliquot_volume() {
		return aliquot_volume;
	}
	public void setAliquot_volume(Float aliquot_volume) {
		this.aliquot_volume = aliquot_volume;
	}

	@Column(name = "num_aliq", nullable = true)
	public Integer getNum_aliq() {
		return num_aliq;
	}
	public void setNum_aliq(Integer num_aliq) {
		this.num_aliq = num_aliq;
	}

	@Column(name = "working_dilution", nullable = true, length = 50)
	public String getWorking_dilution() {
		return working_dilution;
	}
	public void setWorking_dilution(String working_dilution) {
		this.working_dilution = working_dilution;
	}

	@Column(name = "infection_percentage", nullable = true, length = 50)
	public String getInfection_percentage() {
		return infection_percentage;
	}
	public void setInfection_percentage(String infection_percentage) {
		this.infection_percentage = infection_percentage;
	}

	@Column(name = "cellType", nullable = true, length = 50)
	public String getCellType() {
		return cellType;
	}
	public void setCellType(String cellType) {
		this.cellType = cellType;
	}

	@Column(name = "Time_hpi", nullable = true, length = 50)
	public String getTime_hpi() {
		return Time_hpi;
	}
	public void setTime_hpi(String Time_hpi) {
		this.Time_hpi = Time_hpi;
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
		return rvpId;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RVP))
			return false;
		
		RVP castOther = (RVP) other;

		return (this.getRvpId().equals(castOther.getRvpId()));
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
