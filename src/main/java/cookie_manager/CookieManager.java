package cookie_manager;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

import cookie_manager.exceptions.CannotCreateCookieException;
import dao.CookieDAO;
import model.User;

public class CookieManager {

	
	
	/**
	 * Given a user object creates a new token for the user and saves it into a hashmap
	 * @param user
	 * @return
	 * @throws CannotCreateCookieException
	 */
	public String assignCookie(User user) throws CannotCreateCookieException {
		SecureRandom random = new SecureRandom();
		byte[] token = random.getSeed(20);
		String stringToken = Base64.getEncoder().encodeToString(token);
		HashMap<User, String> storage = CookieDAO.instance.getModel();
		storage.put(user, stringToken);
		return stringToken;
	}	
	
	/**
	 * Check if a given users token matches
	 * @param user
	 * @param stringToken
	 * @return
	 */
//	public boolean checkCookie(User user, String stringToken) {
//		HashMap<User, String> cookieModel = CookieDAO.instance.getModel();
//	}
	
	
}
