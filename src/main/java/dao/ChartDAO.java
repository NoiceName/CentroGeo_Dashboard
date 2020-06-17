package dao;

<<<<<<< Updated upstream
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import model.EdgeAppearance;
import model.EdgeAppearancePoint;
=======
import java.sql.PreparedStatement;

import org.json.JSONObject;

>>>>>>> Stashed changes
import model.Database;

public enum ChartDAO {
	instance;
<<<<<<< Updated upstream
	
	
	
//	public JSONObject getVehicleOverTime(int simulation_id, int vehicle_id) {
//		Database db = new Database();
//		Database.loadPGSQL();
//		db.connectPGSQL();
//		String statement = "";
//		PreparedStatement ps = db.prepareStatement();
//	}

//Unfinished code finish debeloping end point when net.net has been uploaded
//select s.time as time, xpath('//vehicle[@id="v0"]/@speed', s.data) as speed, xpath('//vehicle[@id="v0"]/@speedFactor', s.data) as speedFactor, 
//	from projectschema.snapshot s
//	where s.simulation = 1
//	order by s.time) as vehicle);
	
	/**
	 * Generates specified EdgeAppearanceFrequency chart.
	 * @param simulationId - ID of a simulation
	 * @param edgeId - Id of the specified edge for which the chart should be generated.
	 * @return chart object
	 */
	public EdgeAppearance getEdgeAppereance(int simulationId, String edgeId) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = "select ct.time as time, count(ct.data) as counter\r\n" + 
				"from (select s.time as time, unnest(xpath('//route/@edges', s.data))::text as data \r\n" + 
				"from projectschema.snapshot s \r\n" + 
				"where s.simulation = ?) as ct\r\n" + 
				"where ct.data like '% '|| ? ||' %'\r\n" + 
				"or (ct.data like '% ' || ?\r\n" + 
				"or ct.data like ? || ' %')\r\n" + 
				"group by ct.time\r\n" + 
				"order by ct.time;";
		PreparedStatement ps = db.prepareStatement(statement);	
		ArrayList<EdgeAppearancePoint> points = new ArrayList<>();
		try {
			ps.setInt(1, simulationId);
			ps.setString(2, edgeId);
			ps.setString(3, edgeId);
			ps.setString(4, edgeId);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int count = result.getInt("counter");
				double time = result.getFloat("time");
				//Creating points on an EdgeAppearanceChart.
				EdgeAppearancePoint point = new EdgeAppearancePoint(time, count);
				points.add(point);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Create a chart with the given points.
		EdgeAppearance chart = new EdgeAppearance(points, edgeId);
		return chart; 
	}
	
	
	
	/**
	 * Generates specified EdgeAppearanceFrequency chart.
	 * @param simulationId - ID of a simulation
	 * @param edgeId - Id of the specified edge for which the chart should be generated.
	 * @return chart object
	 */
	public EdgeAppearance getTransiting_vehicles(int simulationId, String laneId) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = "SELECT s.time as time, unnest(xpath('//lane[@id=\"?\"]/vehicles/@value', s.data))::text as vehicles\r\n" + 
				"FROM projectschema.snapshot s\r\n" + 
				"WHERE s.simulation = ?\r\n" + 
				"ORDER BY time ASC\r\n";
		PreparedStatement ps = db.prepareStatement(statement);
		
		ArrayList<EdgeAppearancePoint> points = new ArrayList<>();
		try {
			ps.setString(1, laneId);
			ps.setInt(2, simulationId);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int count = result.getInt("counter");
				double time = result.getFloat("time");
				//Creating points on an EdgeAppearanceChart.
				EdgeAppearancePoint point = new EdgeAppearancePoint(time, count);
				points.add(point);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Create a chart with the given points.
		EdgeAppearance chart = new EdgeAppearance(points, edgeId);
		return chart; 
		
	}
	
	
	
=======

	public JSONObject getTransitingVehicles(int simulation_id) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = ("");
		PreparedStatement st = db.prepareStatement(statement);
		
		return null;
	}
>>>>>>> Stashed changes
}
