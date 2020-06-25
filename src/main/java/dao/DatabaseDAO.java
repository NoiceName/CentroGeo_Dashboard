package dao;

import model.Database;

import java.sql.Connection;
import java.sql.SQLException;

public enum DatabaseDAO {
	instance;

	private Database db;

	public void setupDatabase(){
		db = new Database();
		db.nloadPGSQL();
		db.connectPGSQL();
		try {
			db.getConnection().setTransactionIsolation(db.getConnection().TRANSACTION_SERIALIZABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Database getDatabase(){
		if (db == null){
			setupDatabase();
		}
		return db;
	}
}
