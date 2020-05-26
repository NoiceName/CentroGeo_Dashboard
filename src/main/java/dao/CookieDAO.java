package dao;

import java.util.HashMap;

import model.User;

public enum CookieDAO {
	instance;
	HashMap<User, String> cookies = new HashMap<>();
	
	public HashMap<User, String> getModel() {
		return this.cookies;
	}

}
