package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import extraction.ZipExtraction;
import model.Database;
import model.Simulation;

import javax.xml.parsers.ParserConfigurationException;

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

	public int addEmptyMetadata() throws SQLException {
		Database db = DatabaseDAO.instance.getDatabase();

		String query = "INSERT INTO projectschema.simulation(name, date, tags, description) " +
				"VALUES (NULL, NULL, NULL, NULL) RETURNING simulation_id";

		PreparedStatement statement = db.prepareStatement(query);
		statement.execute();

		ResultSet returning = statement.getResultSet();
		returning.next();
		return returning.getInt(1);
	}

	public void addMetadata(int simulationID, String name, String date, String tags, String description) throws SQLException, ParseException {
		Database db = DatabaseDAO.instance.getDatabase();

		//language=PostgreSQL
		String query = "UPDATE projectschema.simulation SET name=?, date=?, tags=?, description=? WHERE simulation_id=?";

		PreparedStatement statement = db.prepareStatement(query);
		statement.setInt(5, simulationID);
		statement.setString(1, name);

		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		java.util.Date fixedDate = format.parse(date);
		statement.setDate(2, new java.sql.Date(fixedDate.getTime()));

		statement.setString(3, tags);
		statement.setString(4, description);

		statement.executeUpdate();
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
		// ZipExtraction.getZipData(stream);
		System.out.println("File added to database");
	}

}
