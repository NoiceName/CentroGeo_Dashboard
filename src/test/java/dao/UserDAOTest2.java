package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserDAOTest2 {

	@Test
	void testGetUserPasswpord() {
		String username = "user1";
		String password = "123456";
		
		assertTrue(password.equals(UserDAO.instance.getUserPassword(username)));
		
	}
	

}
