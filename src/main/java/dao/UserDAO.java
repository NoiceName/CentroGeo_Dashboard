package dao;

import java.util.HashMap;
import java.util.Map;

import model.User;

public enum UserDAO {
	instance;
	private Map<String, User> items = new HashMap<String, User>();
	public Map<String, User> getModel() {
		return items;
	}
}
