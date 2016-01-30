package org.brisskit.i2b2.webapp.applekit;

import java.io.Serializable;

public class Patient implements Serializable {
 
//  private static final long serialVersionUID = -6470090944414208496L;
     
    private String name;
    private int id;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    
    
}
