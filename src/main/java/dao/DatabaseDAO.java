package dao;

import model.Database;

import java.sql.Connection;

public enum DatabaseDAO {
	instance;

	private Database db;

	public void setupDatabase(){
		db = new Database();
		db.nloadPGSQL();
		db.connectPGSQL();
	}

	public Database getDatabase(){
		return db;
	}
}
