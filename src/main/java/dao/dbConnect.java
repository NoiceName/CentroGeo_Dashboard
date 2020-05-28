package dao;

import javax.sql.*;
import javax.naming.*;

public class dbConnect {
//	private static String dbUrl = "jdbc:postgresql://bronto.ewi.utwente.nl/dab_di19202b_124";
//	private static String dbUserName = "dab_di19202b_124";
//	private static String dbPassword = "qY3D5KASvWJbHQpX";
//	private static String dbDriver = "org.postgresql.Driver";
	
	private static DataSource myData = null;
	private static Context context = null;
	private static Context envContext = null;
	public static DataSource getDataSource() throws Exception {
		if(myData != null) {
			return myData;
		}
		try {
			if(context ==null) {
				context = new InitialContext();
				envContext = (Context) context.lookup("java:comp/env");
			}
			myData = (DataSource)envContext.lookup("jdbc/myDB");			
		}
		catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return myData;
	}
}
