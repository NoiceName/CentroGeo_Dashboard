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
	 */
	public static String assignCookie(User user) {
		BiMap<String, User> storage = CookieDAO.instance.getModel();
		String stringToken = generateToken();
		if(storage.inverse().containsKey(user)) {
			storage.inverse().put(user, stringToken);
		} else {
			storage.put(stringToken, user);
		}
		System.out.println(stringToken);
		return stringToken;
	}

	/**
	 * Generates a secure token
	 * @return
	 */
	private static String generateToken() {
		byte[] token = SecureRandom.getSeed(20);
		return Base64.getEncoder().encodeToString(token);
	}	
	
	/**
	 * Check if a given users token exists
	 * @param user
	 * @param stringToken
	 * @return
	 */
	public static boolean checkCookie(String stringToken) {
		BiMap<String, User> cookieModel = CookieDAO.instance.getModel();
		return cookieModel.containsKey(stringToken);
	}
	
	
	/**
	 * Check if the given connection is authenticated
	 * @return
	 */
	public static boolean checkAuth() {
		return false;
	}
	
}
