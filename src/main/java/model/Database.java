package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	//Specify the parameters for the database here!
	//The URL of the database (Note: the url string should begin with 'jdbc::postgresq:' "
	//The username of the database user
	String url = "jdbc:postgresql://localhost:5432/";
	String username = "postgres";
	String password = "password";
	//Do not change this variable
	String schemaName = "projectschema";

	Connection connection;

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

	public void nloadPGSQL(){
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connects to the specified PGSQL database
	 * @return
	 */
	public Connection connectPGSQL() {
		Properties props = new Properties();
		props.setProperty("user", this.username);
		props.setProperty("password", this.password);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(this.url, props);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.connection = conn;
		return conn;

	}

	/**
	 * Creates a prepared statement from the connected database
	 * @param preparedStatement
	 * @return
	 */
	public PreparedStatement prepareStatement(String preparedStatement) {
		try {
			return this.connection.prepareStatement(preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getSchema() {
		return this.schemaName;
	}

	public Connection getConnection() {
		return this.connection;
	}

}
