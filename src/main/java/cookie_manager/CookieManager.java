package cookie_manager;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

import com.google.common.collect.BiMap;

import dao.CookieDAO;
import model.User;

public class CookieManager {

	/**
	 * Given a user object creates a new token for the user and saves it into a hashmap.
	 * In the case a user already has a token update it.
	 * @param user
	 * @return
	 * @throws CannotCreateCookieException
	 */
	public static String assignCookie(User user) {
		BiMap<String, User> storage = CookieDAO.instance.getModel();
		String stringToken = generateToken();
		if(storage.inverse().containsKey(user)) {
			storage.inverse().put(user, stringToken);
		} else {
			storage.put(stringToken, user);
		}
		return stringToken;
	}

	/**
	 * Generates a token
	 * @return
	 */
	private static String generateToken() {
		SecureRandom random = new SecureRandom();
		byte[] token = random.getSeed(20);
		String stringToken = Base64.getEncoder().encodeToString(token);
		return stringToken;
	}	
	
	/**
	 * Check if a given users token exists
	 * @param user
	 * @param stringToken
	 * @return
	 */
	public static boolean checkCookie(String stringToken) {
		BiMap<String, User> cookieModel = CookieDAO.instance.getModel();
		if(cookieModel.containsKey(stringToken)) {
			return true;
		}
		else {return false;}
	}


}
