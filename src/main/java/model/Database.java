package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	//Specify the parameters for the database here!
	//The URL should be of the following format jdbc:postgresql://localhost/test
	String url = "jdbc:postgresql://bronto.ewi.utwente.nl/";
	String username = "dab_di19202b_2";
	String password = "3JvJt7ETzXVak62M";
	String schemaName = "prpjectSchema";

	/**
	 * Saves a database with given parameters
	 * @param url
	 * @param username
	 * @param password
	 */
	public Database(String url, String username, String password, String schemaName) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Instantiates a database with default parameters
	 */
	public Database() {
	}
	
	/**
	 * Loads the driver for a pgsql database.
	 */
	public static void loadPGSQL(){
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Connects to the specified database
	 * @return
	 */
	public Connection connect() {
		Properties props = new Properties();
		props.setProperty("user", this.username);
		props.setProperty("password", this.password);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(this.url, props);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}


}
