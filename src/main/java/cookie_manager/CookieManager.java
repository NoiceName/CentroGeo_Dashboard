package cookie_manager;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

import dao.CookieDAO;
import model.User;

public class CookieManager {

	
	
	/**
	 * Given a user object creates a new token for the user and saves it into a hashmap
	 * @param user
	 * @return
	 * @throws CannotCreateCookieException
	 */
	public static String assignCookie(User user) {
		SecureRandom random = new SecureRandom();
		byte[] token = random.getSeed(20);
		String stringToken = Base64.getEncoder().encodeToString(token);
		HashMap<String, User> storage = CookieDAO.instance.getModel();
		storage.put(stringToken, user);
		return stringToken;
	}	
	
	/**
	 * Check if a given users token exists
	 * @param user
	 * @param stringToken
	 * @return
	 */
	public static boolean checkCookie(String stringToken) {
		HashMap<String, User> cookieModel = CookieDAO.instance.getModel();
		if(cookieModel.containsKey(stringToken)) {
			return true;
		}
		else {return false;}
	}

	

}
