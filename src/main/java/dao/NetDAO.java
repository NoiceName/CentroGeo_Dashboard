package dao;

import model.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum NetDAO {
	instance;

	public void addNet(String id, String edge_id, int simulationID, int index, double length, String shape) throws SQLException {
		Database db = DatabaseDAO.instance.getDatabase();

		//language=PostgreSQL
		String query = "INSERT INTO projectschema.lane (lane_id, edge_id, simulation, index, length, shape) " +
				"VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, id);
		statement.setString(2, edge_id);
		statement.setInt(3, simulationID);
		statement.setInt(4, index);
		statement.setDouble(5, length);
		statement.setString(6, shape);

		statement.executeUpdate();
	}
}
