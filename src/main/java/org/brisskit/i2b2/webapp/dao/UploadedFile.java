package org.brisskit.i2b2.webapp.dao;

import java.io.Serializable;

/** UploadedFile
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public class UploadedFile implements Serializable {

	public static final long serialVersionUID = -38331060124340967L;
	public String name;
	public Integer size;
	public String url;
	public String username;
	public String projectname;
	
	public UploadedFile() {
		super();
	}
	public UploadedFile(String name, Integer size, String url, String username, String projectname) {
		super();
		this.name = name;
		this.size = size;
		this.url = url;
		this.username = username;
		this.projectname = projectname;
	}
	public UploadedFile(String name, Integer size, String url,
			String thumbnail_url, String delete_url, String delete_type, String username, String projectname) {
		super();
		this.name = name;
		this.size = size;
		this.url = url;
		this.username = username;
		this.projectname = projectname;
	}
} 
