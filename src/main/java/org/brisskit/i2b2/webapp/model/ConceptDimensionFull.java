package org.brisskit.i2b2.webapp.model;

// default package
// Generated 25-Jan-2015 14:11:32 by Hibernate Tools 3.2.0.beta8

import java.util.Date;

/**
 * ConceptDimension generated by hbm2java
 */
public class ConceptDimensionFull implements java.io.Serializable {

	// Fields    

	public String concept_path;

	public String concept_cd;

	public String name_char;
	
	public String concept_blob;

	public Date update_date;

	public Date download_date;

	public Date import_date;

	public String sourcesystem_cd;

	public Integer upload_id;

	public String getConcept_path() {
		return concept_path;
	}

	public void setConcept_path(String concept_path) {
		this.concept_path = concept_path;
	}

	public String getConcept_cd() {
		return concept_cd;
	}

	public void setConcept_cd(String concept_cd) {
		this.concept_cd = concept_cd;
	}

	public String getName_char() {
		return name_char;
	}

	public void setName_char(String name_char) {
		this.name_char = name_char;
	}

	public String getConcept_blob() {
		return concept_blob;
	}

	public void setConcept_blob(String concept_blob) {
		this.concept_blob = concept_blob;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Date getDownload_date() {
		return download_date;
	}

	public void setDownload_date(Date download_date) {
		this.download_date = download_date;
	}

	public Date getImport_date() {
		return import_date;
	}

	public void setImport_date(Date import_date) {
		this.import_date = import_date;
	}

	public String getSourcesystem_cd() {
		return sourcesystem_cd;
	}

	public void setSourcesystem_cd(String sourcesystem_cd) {
		this.sourcesystem_cd = sourcesystem_cd;
	}

	public Integer getUpload_id() {
		return upload_id;
	}

	public void setUpload_id(Integer upload_id) {
		this.upload_id = upload_id;
	}

	// Constructors

	
}
