package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * define a user, the name uniquely identifies each user 
 */
@XmlRootElement
public class User {
     private String name;
     private String password;
 

	public User(){
    	 
     }
     
    public User(String name, String password) {
 
    	 setName(name);
    	 setPassword(password);
     }

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}    

}
