package model;

import database_parameter_manager.DatabaseParameterManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	//Specify the parameters for the database here!
	//The URL of the database (Note: the url string should begin with 'jdbc::postgresq:' "
	//The username of the database user
	private static String url = "";
	private static String username = "";
	private static String password = "";

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
	 * Read database parameters from the db_log.json file
	 */
	public static void readInParametersFromStorage() {
		JSONArray arr = DatabaseParameterManager.readParameters();
		//If there are no parameters or they have not been set
		if (arr.equals(new JSONArray())) {
			System.out.println("The parameter file is empty not setting parameters");
			setUsername("");
			setUrl("");
			setPassword("");
		} else {
			System.out.println("Setting database parameters from storage");
			JSONObject params = (JSONObject) arr.get(0);
			setUsername((String) params.get("username"));
			setUrl((String) params.get("url"));
			setPassword((String) params.get("password"));
		}
		System.out.println("The currently used parameters are:");
		System.out.println(DatabaseParameterManager.readParameters());
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

	/**
	 * Closes an open connection for this database
	 */
	public void closeConnection(){
		try {
			this.connection.close();
		} catch (SQLException throwables) {
		    System.out.println(throwables.getMessage());
			throwables.printStackTrace();
		}
	}

	public String getSchema() {
		return this.schemaName;
	}

	public static void setParameters(String username, String password, String url){
		DatabaseParameterManager.writeParameters(username,password,url);
		setUsername(username);
		setPassword(password);
		setUrl(url);
	}

	private static void setUrl(String pasurl){
		url = pasurl;
	}

	private static void setPassword(String pass){
		password = pass;
	}

	private static void setUsername(String user){
		username = user;
	}

	public Connection getConnection() {
		return connection;
	}

	public static String getPassword(){
		return password;
	}

	public static String getUrl(){
		return url;
	}

	public static String getUsername(){
		return username;
	}


}
