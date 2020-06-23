package dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import model.Database;
import model.User;

public enum UserDAO {
	instance;
		
	public String getUserPassword(String userName){
		
		PreparedStatement query = null;
		String returnPassword = null;
		Connection conn = null;
		
		Database.loadPGSQL();
	    Database db = new Database();
		
		try {	
			conn = db.connectPGSQL();

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
		finally {
			
			if(conn!=null){
			   try {
				 
				   conn.close();
				
			   } catch (SQLException e) {
				e.printStackTrace();
			   }
			}
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
		Connection conn = null;
		
		Database.loadPGSQL();
	    Database db = new Database();
		
		try {	
			Database.loadPGSQL();
			conn = db.connectPGSQL();

			String sql = "insert into projectschema.user "+
					"values(?,?); ";
			query = conn.prepareStatement(sql);
			query.setString(1, user.getUserName());
			String password = user.getPassword();
			
			//hash_password using Argon2
//			Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
//			String passwordHash = argon2.hash(4, 1024 * 1024, 8, password);
			
			//hasd_password using SCryptPasswordEncoder
			int cpuCost = (int) Math.pow(2, 14); // factor to increase CPU costs
			int memoryCost = 8;      // increases memory usage
			int parallelization = 1; // currently not supported by Spring Security
			int keyLength = 32;      // key length in bytes
			int saltLength = 64;     // salt length in bytes

			SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(
			  cpuCost, 
			  memoryCost,
			  parallelization,
			  keyLength,
			  saltLength);
			String hashedPassword = sCryptPasswordEncoder.encode(password);
			
			query.setString(2, hashedPassword);			
			return query.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0; // User data not entered;
		}
		finally {
			query.close();
			conn.close();
		}
		
	}	
}


