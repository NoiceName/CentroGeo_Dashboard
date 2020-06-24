package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	
	public int editMetadata(int title,String editor, Date date, String tag, String description){
		
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

	public void deleteSimulation(int id){
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String query = "DELETE FROM projectschema.simulation WHERE simulation_id = ?";
		PreparedStatement statement = db.prepareStatement(query);
		try {
			statement.setInt(1, id);
			statement.executeUpdate();
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
	
	public ArrayList<Integer> getSimulationIds() {
		Database db = new Database(); 
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement= "SELECT sim.simulation_id\r\n" + 
						  "FROM projectschema.simulation sim\r\n";

		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<Integer> simIDs = new ArrayList<>(); 
		
	    try {
	    	ResultSet result = ps.executeQuery();
	    	while(result.next()) {
	    		simIDs.add(result.getInt("simulation_id"));
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		    return simIDs;
	}
	
	public ArrayList<Integer> getLaneIds(int simulationId) {
		Database db = new Database(); 
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement= "SELECT l.lane_id\r\n" + 
						  "FROM projectschema.lane l\r\n" + 
				          "WHERE l.simulation = ?";

		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<Integer> laneIDs = new ArrayList<>(); 
		
	    try {
	    	ps.setInt(1, simulationId);
	    	ResultSet result = ps.executeQuery();
	    	while(result.next()) {
	    		laneIDs.add(result.getInt("simulation_id"));
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		    return laneIDs;
	}
	
	public ArrayList<Integer> getEdgeIds(int simulationId) {
		Database db = new Database(); 
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement= "SELECT l.lane_id\r\n" + 
						  "FROM projectschema.lane l\r\n" + 
				          "WHERE l.simulation = ?";

		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<Integer> EdgeIDs = new ArrayList<>(); 
		
	    try {
	    	ps.setInt(1, simulationId);
	    	ResultSet result = ps.executeQuery();
	    	while(result.next()) {
	    		EdgeIDs.add(result.getInt("simulation_id"));
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		    return EdgeIDs;
	}

}
