package cookie_manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import dao.CookieDAO;
import dao.UserDAO;
import model.User;

class CookieManagerTest {


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
		String token = CookieManager.assignCookie(max);
		String token2 = CookieManager.assignCookie(sam);
		
		//Test whether the given tokens match
		assertTrue(CookieManager.checkCookie(token));
		assertFalse(CookieManager.checkCookie("fakeToken"));
	
	}	
	
}
