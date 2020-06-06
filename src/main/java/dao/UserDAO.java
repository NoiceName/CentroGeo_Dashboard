package dao;

import java.sql.*;

import model.User;

public enum UserDAO {
	instance;
//	private Map<String, User> users = new HashMap<String, User>();
	
//	private UserDAO() {
//		User user1 = new User("user1","123456");
//		users.put("user1", user1);
//	}
	
//	public Map<String, User> getModel() {
//		return users;
//	}
	
	public String getUserPassword(String userName){
		PreparedStatement query = null;
		String returnPassword = null;
		Connection conn = null;
		
		try {
			conn = databaseConnection.getConnection();

			String sql = "select password from projectschema.user "+
			"where username = ? ";
			query = conn.prepareStatement(sql);
			query.setString(1, userName);
			ResultSet rs = query.executeQuery();
			// check if the user in the table and is with a correct password
			while(rs.next()) {
//				returnPassword = rs.getString("password");
				returnPassword  = rs.getString(2);
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
		 * 2:user already exists, change a user name
		 */
		PreparedStatement query = null;
		int returnString; // user data entered successfully!;
		Connection conn = null;
		
		try {	
			conn = databaseConnection.getConnection();			
			if(getUserPassword(user.getUserName())!=null) {
				
				return 2; // user already exists
			}
			
			String sql = "insert into projectschema.user "+
					"values(?,?); ";
			query = conn.prepareStatement(sql);
			query.setString(1, user.getUserName());
			query.setString(2, user.getPassword());
			returnString = query.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			returnString = 0; // User data not entered;
		}
		finally {
			query.close();
			conn.close();
		}
		return returnString;		
	}	
}


