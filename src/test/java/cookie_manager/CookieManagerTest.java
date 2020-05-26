package cookie_manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import dao.CookieDAO;
import dao.UserDAO;
import model.User;

class CookieManagerTest {


	@Test
	void assignCookieTest() {
		User user = new User();
		CookieManager.assignCookie(user);
		String result = CookieDAO.instance.getModel().get(user);
		assertTrue(result instanceof String);
	}
	
	@Test
	void testIfCookiesExists() {
		User user1 = new User("max", "amazingPassword");
		User user2 = new User("sam", "123456");
		
		//Create new users 
		HashMap<String, User> userMap = (HashMap<String, User>) UserDAO.instance.getModel();
		userMap.put(user1.getUserName(), user1);
		userMap.put(user2.getUserName(), user2);

		User max = userMap.get("max");
		User sam = userMap.get("sam");
		
		
		//Assing cookie tokens to users
		CookieManager.assignCookie(max);
		CookieManager.assignCookie(sam);
		
		//Test whether the given tokens match
		assertTrue(CookieManager.checkCookie(max, CookieDAO.instance.getModel().get(max)));
		assertFalse(CookieManager.checkCookie(sam, CookieDAO.instance.getModel().get(max)));
	
	}	
	
}
