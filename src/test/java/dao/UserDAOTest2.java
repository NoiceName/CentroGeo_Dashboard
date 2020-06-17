package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import model.User;

class UserDAOTest2 {
	
	
	@Test 
	void testInsertUser() throws Exception {
		//insert an existing username and password
		User user1= new User("user1","123456");	
		assertEquals(0, UserDAO.instance.insertUser(user1));
		
	}
	
	@Test
	void testGetUserPasswpord() {
		//already stored in database with hashed password of "123456"
		String username = "user1";
		String password = "123456";
		
        String returnPassword = UserDAO.instance.getUserPassword(username);
        
		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);	
		
		boolean success = argon2.verify(returnPassword, password);
		
		assertTrue(success);	
		
				
	}
	

	

}
