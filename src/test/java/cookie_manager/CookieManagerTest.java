package cookie_manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import dao.CookieDAO;
import dao.UserDAO;
import model.User;

class CookieManagerTest {

//	@Test
//	void testIfCookiesExists() {
//		User user1 = new User("max", "amazingPassword");
//		User user2 = new User("sam", "123456");
//
//		//Create new users
//		HashMap<String, User> userMap = (HashMap<String, User>) UserDAO.instance.getModel();
//		userMap.put(user1.getUserName(), user1);
//		userMap.put(user2.getUserName(), user2);
//
//		User max = userMap.get("max");
//		User sam = userMap.get("sam");
//
//		//Assing cookie tokens to users
//		String token = CookieManager.assignCookie(max);
//		String token2 = CookieManager.assignCookie(sam);
//
//		//Test whether the given tokens match
//		assertTrue(CookieManager.checkCookie(token));
//		assertFalse(CookieManager.checkCookie("fakeToken"));
//
//	}
//
//	@Test
//	void updateCookies() {
//		User user1 = new User();
//		String firstToken = CookieManager.assignCookie(user1);
//		String secondToken = CookieManager.assignCookie(user1);
//		assertFalse(CookieManager.checkCookie(firstToken));
//		assertTrue(CookieManager.checkCookie(secondToken));
//	}
	
	@Test
	void testIfCookiesExists() {
		User user1 = new User("max", "amazingPassword");
		User user2 = new User("sam", "123456");

		//insert new users into database

		try {

			UserDAO.instance.insertUser(user1);
			UserDAO.instance.insertUser(user2);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String password1 = UserDAO.instance.getUserPassword("max");
		String password2 = UserDAO.instance.getUserPassword("sam");

		User max = new User("max",password1);
		User sam = new User("sam",password2);

		//Assing cookie tokens to users
		String token = CookieManager.assignCookie(max);
		String token2 = CookieManager.assignCookie(sam);

		//Test whether the given tokens match
		assertTrue(CookieManager.checkCookie(token));
		assertFalse(CookieManager.checkCookie("fakeToken"));

	}

	@Test
	void updateCookies() {
		User user1 = new User();
		String firstToken = CookieManager.assignCookie(user1);
		String secondToken = CookieManager.assignCookie(user1);
		assertFalse(CookieManager.checkCookie(firstToken));
		assertTrue(CookieManager.checkCookie(secondToken));
	}

}
