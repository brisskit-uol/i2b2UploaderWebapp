package org.brisskit.i2b2.webapp.model;

public class OntMappings implements java.io.Serializable {

	int id;
	String project_id;
	String username;
	String current_code;
	String new_code;
	String new_code_desc;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurrent_code() {
		return current_code;
	}
	public void setCurrent_code(String current_code) {
		this.current_code = current_code;
	}
	public String getNew_code() {
		return new_code;
	}
	public void setNew_code(String new_code) {
		this.new_code = new_code;
	}
	public String getNew_code_desc() {
		return new_code_desc;
	}
	public void setNew_code_desc(String new_code_desc) {
		this.new_code_desc = new_code_desc;
	}

	
}
