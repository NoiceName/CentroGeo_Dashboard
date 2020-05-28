package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class databaseConnection {
	private static String dbUrl = "jdbc:postgresql://bronto.ewi.utwente.nl/dab_di19202b_124";
	private static String dbUserName = "dab_di19202b_124";
	private static String dbPassword = "qY3D5KASvWJbHQpX";
	private static String dbDriver = "org.postgresql.Driver";
	private static Connection conn = null;
	
	public static Connection getConnection() throws Exception{
		if(conn != null) {
			return conn;
		}
	
		try { 
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
			
		} 
		catch (ClassNotFoundException e) {
			System.err.println("JDBC driver not loaded");
		}
		catch (SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		    }
		return conn;
	}
}
