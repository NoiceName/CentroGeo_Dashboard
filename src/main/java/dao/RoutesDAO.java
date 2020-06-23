package dao;

import model.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLXML;

public enum RoutesDAO {
	instance;

	public void addRoute(int simulationID, String id, float depart) throws SQLException {
		Database db = new Database();
		db.connectPGSQL();

		String query = "INSERT INTO projectschema.vehicle (vehicle_id, simulation, depart) " +
				"VALUES (?, ?, ?)";

		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, id);
		statement.setInt(2, simulationID);
		statement.setFloat(3, depart);

		statement.executeUpdate();
		db.getConnection().close();

	}
}
