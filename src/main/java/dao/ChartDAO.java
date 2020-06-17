package dao;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import model.Chart;
import model.ChartPoint;
import model.Database;

public enum ChartDAO {
	instance;
	
	
	
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
	public Chart getEdgeAppereance(int simulationId, String edgeId) {
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
		ArrayList<ChartPoint> points = new ArrayList<>();
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
				ChartPoint point = new ChartPoint(time, count);
				points.add(point);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Create a chart with the given points.
		Chart chart = new Chart(points, edgeId);
		return chart; 
	}
	
	
	
	/**
	 * Generates specified EdgeAppearanceFrequency chart.
	 * @param simulationId - ID of a simulation
	 * @param edgeId - Id of the specified edge for which the chart should be generated.
	 * @return chart object
	 */
	public Chart getTransiting_vehicles(int simulationId, String laneId) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = "SELECT ct.time, ct.vehicles\r\n" + 
				"FROM (SELECT s.time as time, unnest(xpath('//lane/@id', s.data))::text as laneId, unnest(xpath('//lane/vehicles/@value', s.data))::text as vehicles\r\n" + 
				"	FROM projectschema.snapshot s\r\n" + 
				"	WHERE s.simulation = ?) as ct\r\n" + 
				"WHERE laneId = ?\r\n" + 
				"ORDER BY ct.time ASC";
		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<ChartPoint> points = new ArrayList<>();
		try {
			ps.setInt(1, simulationId);
			ps.setString(2, laneId);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				String[] cars = result.getString("vehicles").split("v");
				int count = cars.length -1;
				double time = result.getFloat("time");
				//Creating points on an EdgeAppearanceChart.
				ChartPoint point = new ChartPoint(time, count);
				points.add(point);
			}
			System.out.println("all points added for " + laneId);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Create a chart with the given points.
		Chart chart = new Chart(points, laneId);
		return chart; 
		
	}
	
}
