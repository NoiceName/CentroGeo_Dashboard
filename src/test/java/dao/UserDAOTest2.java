package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

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
		String rawPassword = "123456";
		
        String returnHashedPassword = UserDAO.instance.getUserPassword(username);
        
		int cpuCost = (int) Math.pow(2, 14); // factor to increase CPU costs
		int memoryCost = 8;      // increases memory usage
		int parallelization = 1; // parallelization
		int keyLength = 32;      // key length in bytes
		int saltLength = 64;     // salt length in bytes

		SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(
		  cpuCost, 
		  memoryCost,
		  parallelization,
		  keyLength,
		  saltLength);
		boolean match = sCryptPasswordEncoder.matches(rawPassword, returnHashedPassword);
        
//		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);	
//		
//		boolean success = argon2.verify(returnPassword, password);
		
		assertTrue(match);	
		
				
	}
	

	

}
