package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Database;

class DatabaseTest {

	//Specify the database parameters before running this test in the Database class
	@Test
	void databaseConnecntionTest() {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
	}

}
