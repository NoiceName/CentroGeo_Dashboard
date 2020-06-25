package dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import model.Database;
import model.User;

public enum UserDAO {
	instance;

	public void setUserPassword(String userName, String hashPassword){
		Database db = DatabaseDAO.instance.getDatabase();
		String query = "UPDATE projectschema.user SET password_hash = ? WHERE username = ?";
		PreparedStatement statement = db.prepareStatement(query);
		try {
			statement.setString(1, hashPassword);
			statement.setString(2, userName);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public String getUserPassword(String userName){
		
		PreparedStatement query = null;
		String returnPassword = null;
		Connection conn = null;

		Database db = DatabaseDAO.instance.getDatabase();
		
		try {	
			conn = db.getConnection();

			String sql = "select password_hash from projectschema.user "+
			"where username = ? ";
			query = conn.prepareStatement(sql);
			query.setString(1, userName);
			ResultSet rs = query.executeQuery();
			// check if the user in the table and is with a correct password
			while(rs.next()) {
//				returnPassword = rs.getString("password");
				returnPassword  = rs.getString("password_hash");
			}
			query.close();		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return returnPassword;
	}
	
	public int insertUser(User user)throws Exception {
		/**
		 * return number
		 * 0:User sql exception, data not entered
		 * 1:user data entered successfully!
		 */
		PreparedStatement query = null;
		int returnString = 0; // user data entered successfully!;
		Connection conn = null;

		Database db = DatabaseDAO.instance.getDatabase();
		
		try {	
			Database.loadPGSQL();
			conn = db.getConnection();

			String sql = "insert into projectschema.user "+
					"values(?,?); ";
			query = conn.prepareStatement(sql);
			query.setString(1, user.getUserName());
			
			//hash_password
			String password = user.getPassword();
			Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
			String passwordHash = argon2.hash(4, 1024 * 1024, 8, password);
			query.setString(2, passwordHash);			
			returnString = query.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			returnString = 0; // User data not entered;
		}
		finally {
			query.close();
		}
		
		return returnString;		
	}	
}


