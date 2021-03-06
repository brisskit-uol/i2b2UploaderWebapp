package org.brisskit.i2b2.webapp.model;

// default package
// Generated 25-Jan-2015 14:11:32 by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * PatientMapping generated by hbm2java
 */
public class PatientMappingX implements java.io.Serializable {

	// Fields    

	private PatientMappingId id;

	private int patientNum;

	private String patientIdeStatus;

	private Date uploadDate;

	private Date updateDate;

	private Date downloadDate;

	private Date importDate;

	private String sourcesystemCd;

	private Integer uploadId;

	// Constructors

	/** default constructor */
	public PatientMappingX() {
	}

	/** minimal constructor */
	public PatientMappingX(PatientMappingId id, int patientNum) {
		this.id = id;
		this.patientNum = patientNum;
	}

	/** full constructor */
	public PatientMappingX(PatientMappingId id, int patientNum,
			String patientIdeStatus, Date uploadDate, Date updateDate,
			Date downloadDate, Date importDate, String sourcesystemCd,
			Integer uploadId) {
		this.id = id;
		this.patientNum = patientNum;
		this.patientIdeStatus = patientIdeStatus;
		this.uploadDate = uploadDate;
		this.updateDate = updateDate;
		this.downloadDate = downloadDate;
		this.importDate = importDate;
		this.sourcesystemCd = sourcesystemCd;
		this.uploadId = uploadId;
	}

	// Property accessors
	public PatientMappingId getId() {
		return this.id;
	}

	public void setId(PatientMappingId id) {
		this.id = id;
	}

	public int getPatientNum() {
		return this.patientNum;
	}

	public void setPatientNum(int patientNum) {
		this.patientNum = patientNum;
	}

	public String getPatientIdeStatus() {
		return this.patientIdeStatus;
	}

	public void setPatientIdeStatus(String patientIdeStatus) {
		this.patientIdeStatus = patientIdeStatus;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDownloadDate() {
		return this.downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	public Date getImportDate() {
		return this.importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getSourcesystemCd() {
		return this.sourcesystemCd;
	}

	public void setSourcesystemCd(String sourcesystemCd) {
		this.sourcesystemCd = sourcesystemCd;
	}

	public Integer getUploadId() {
		return this.uploadId;
	}

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}

}
