package org.brisskit.i2b2.webapp.model;

// default package
// Generated 22-Jan-2015 15:23:27 by Hibernate Tools 3.2.0.beta8

/**
 * Projects generated by hbm2java
 */
public class Projects implements java.io.Serializable {

	// Fields    

	private int id;

	private String projectId;

	private String username;

	// Constructors

	/** default constructor */
	public Projects() {
	}

	/** full constructor */
	public Projects(String projectId, String username) {
		//this.id = id;
		this.projectId = projectId;
		this.username = username;
	}

	// Property accessors
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
