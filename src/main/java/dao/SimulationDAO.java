package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Database;
import model.Simulation;

public enum SimulationDAO {
	instance;
	
	public ArrayList<Simulation> getSimulations() {
		return new ArrayList<Simulation>();
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

	public void addSimulation(InputStream stream){
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		try{
			Connection connection = db.getConnection();
			System.out.println("Downloaded file");
			extraction.ZipExtraction.getZipData(stream, connection);
			System.out.println("File added to database");
		} catch (IOException e) {
			e.printStackTrace(); }
	}

}
