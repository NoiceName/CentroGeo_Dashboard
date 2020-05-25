package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * define a user, the name uniquely identifies each user 
 */
@XmlRootElement
public class User {

     String username;
     String password;
 
	public User(){
    	 
     }
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUserName() {
		return username;
	}

	protected void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}    
	
}
