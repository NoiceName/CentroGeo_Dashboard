package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * define a user, the name uniquely identifies each user 
 */
@XmlRootElement
public class User {
     private String name;
     private String password;
     private String email;
     


	public User(){
    	 
     }
     
    public User(String name, String password,String email) {
    	 setEmail(email);
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
	
	public String getEmail() {
		return email;
	}

	protected void setEmail(String email) {
		this.email = email;
	}
}
