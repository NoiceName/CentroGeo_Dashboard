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
	
	private String url= "jdbc:postgresql://bronto.ewi.utwente.nl/dab_di19202b_124";
	private String dbName ="dab_di19202b_124";
	private String dbPassword = "qY3D5KASvWJbHQpX";
	private String schemaName = "projectschema";
	
	Database db = new Database(url, dbName, dbPassword, schemaName);
	
	public int editMetadata(int title,String editor, Date date, String tag, String description){
		
		PreparedStatement query = null;
		Connection conn = null;
		int rowsAffected = 0;// failed in updating
		
		try {
			Database.loadPGSQL();
			conn = db.connectPGSQL();

			String sql = 
			"UPDATE projectschema.simulation "+
			"SET name = ?, date = ?, tags = ?,descripton = ? " +
			"WHERE simulation_id = ?; ";
						
			query = conn.prepareStatement(sql);
			query.setInt(1, title);
			query.setString(2, editor);
			query.setDate(3, new java.sql.Date(date.getTime()));
			query.setString(4, tag);
			query.setString(5, description);
			

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
