package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.User;

public enum UserDAO {
	instance;
	private Map<String, User> users = new HashMap<String, User>();
	
	private UserDAO() {
		User user1 = new User("user1","123456");
		users.put("user1", user1);
	}
	
	public Map<String, User> getModel() {
		return users;
	}
	
	public User getUser(String name){
		   
		      for(Entry<String, User> user: users.entrySet()){
		         if(user.getKey() == name){
		            return user.getValue();
		         }
		      }
		      return null;
		   }

	 public int addUser(User pUser){
		      
		      boolean userExists = false;
		      for(Entry<String, User> user: users.entrySet()){
		         if(user.getKey() == pUser.getName()){
		            userExists = true;
		            break;
		         }
		      }		
		      if(!userExists){
		         users.put(pUser.getName(),pUser);
		         return 1; // add successfully
		      }
		      return 0;
		   }
}
