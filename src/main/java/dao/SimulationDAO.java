package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Database;
import model.Simulation;

public enum SimulationDAO {
	instance;
	
	/**
	 * Retrieves an array of all simulations stored in the database.
	 * @return - An array containing all simulations.
	 */
	public ArrayList<Simulation> getSimulations() {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String query = "select * from projectschema.simulation s";
		PreparedStatement st = db.prepareStatement(query);
		ArrayList<Simulation> arr = new ArrayList<>();
		try {
			ResultSet res = st.executeQuery();
			while(res.next()) {
				int id = res.getInt("simulation_id");
				String description = res.getString("description");
				String name = res.getString("name");
				String date = res.getString("date");
				String tags = res.getString("tags");
				Simulation sim = new Simulation(id, name, date, tags, description);
				arr.add(sim);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return arr;
	}
	
	
	public int editMetadata(int title,String name, Date date, String tag, String description){
		
		PreparedStatement query = null;
		Connection conn = null;
		int rowsAffected = 0;// failed in updating
		Database.loadPGSQL();
	    Database db = new Database();	
		try {
			
			conn = db.connectPGSQL();

			String sql = 
			"UPDATE projectschema.simulation "+
			"SET name = ?, date = ?, tags = ?, description = ? " +
			"WHERE simulation_id = ?; ";
						
			query = conn.prepareStatement(sql);
			query.setString(1, name);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			java.sql.Date sqlDate = null;
			if(date != null){		
			String stringDate= dateFormat.format(date);
			sqlDate=  java.sql.Date.valueOf(stringDate);		
			}
			query.setDate(2, sqlDate);
			
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

	public void deleteSimulation(String name){
Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String query = "DELETE FROM projectschema.simulation WHERE name LIKE ?";
		PreparedStatement statement = db.prepareStatement(query);
		try {
			statement.setString(1, name);
			statement.executeUpdate();
			System.out.println("Deleted simulation: " + name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addSimulation(InputStream stream) throws Exception {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();

		Connection connection = db.getConnection();
		System.out.println("Downloaded file");
		extraction.ZipExtraction.getZipData(stream, connection);
		System.out.println("File added to database");
	}

}
