package cookie_manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cookie_manager.exceptions.CannotCreateCookieException;
import dao.CookieDAO;
import model.User;

class CookieManagerTest {


	@Test
	void assignCookieTest() {
		User user = new User();
		CookieManager manager = new CookieManager();
		try {
			manager.assignCookie(user);
		} catch (CannotCreateCookieException e) {
			e.printStackTrace();
		}
		String result = CookieDAO.instance.getModel().get(user);
		assertTrue(result instanceof String);
	}

	
	
}
