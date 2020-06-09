package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Database;
import model.Simulation;

public enum SimulationDAO {
	instance;
	
	public ArrayList<Simulation> getSimulations() {
		return new ArrayList<Simulation>();
	}
	
	public int editMetadata(int title,String editor, Date date, String tag, String description){
		
		PreparedStatement query = null;
		Connection conn = null;
		int rowsAffected = 0;// failed in updating
		Database.loadPGSQL();
	    Database db = new Database();	
		try {
			Database.loadPGSQL();
			conn = db.connectPGSQL();

			String sql = 
			"UPDATE projectschema.simulation "+
			"SET name = ?, date = ?, tags = ?,descripton = ? " +
			"WHERE simulation_id = ?; ";
						
			query = conn.prepareStatement(sql);
			query.setString(1, editor);
			query.setDate(2, new java.sql.Date(date.getTime()));
			query.setString(3, tag);
			query.setString(4, description);
			query.setInt(5, title);

			rowsAffected = query.executeUpdate();// should be 1								
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
					
			   try {
				   query.close();
				   conn.close();
				
			   } catch (SQLException e) {
				e.printStackTrace();
			   
			}
		}	    
		return rowsAffected;
	}

}
