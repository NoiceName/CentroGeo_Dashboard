package dao;

import java.util.HashMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import model.User;

public enum CookieDAO {
	instance;
	BiMap<String, User> cookies = HashBiMap.create();
	
	public BiMap<String, User> getModel() {
		return this.cookies;
	}

}
