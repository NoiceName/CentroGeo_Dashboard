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
	
	
	public Chart getSpeedByTimeChart(int simulation_id, String vehicle_id) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = "select time, speed\r\n" + 
				"from (select s.time, unnest(xpath('//vehicle/@speed', s.data))::text as speed, unnest(xpath('//vehicle/@id', s.data))::text as veh_id\r\n" + 
				"from projectschema.snapshot s\r\n" + 
				"where s.simulation = ?\r\n" + 
				"order by s.time) as vehicles\r\n" + 
				"where veh_id = ?;";
		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<ChartPoint> points = new ArrayList<>();
		Chart chart = null;
		try {
			ps.setInt(1, simulation_id);
			ps.setString(2, vehicle_id);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				double speed = result.getFloat("speed");
				double time = result.getFloat("time");
				//Creating points on an speedByTimeChart.
				ChartPoint point = new ChartPoint(time, speed);
				points.add(point);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Create a chart with the given points.
		chart = new Chart(points, "speed in m/s");
		return chart; 
	}



	public Chart getSpeedFactorByTimeChart(int simulation_id, String vehicle_id) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = "select time, speedFactor\r\n" + 
				"from (select s.time, unnest(xpath('//vehicle/@speedFactor', s.data))::text as speedfactor, unnest(xpath('//vehicle/@id', s.data))::text as veh_id\r\n" + 
				"from projectschema.snapshot s\r\n" + 
				"where s.simulation = ?\r\n" + 
				"order by s.time) as vehicles\r\n" + 
				"where veh_id = ?;";
		PreparedStatement ps = db.prepareStatement(statement);	
		ArrayList<ChartPoint> points = new ArrayList<>();
		Chart chart = null;
		try {
			ps.setInt(1, simulation_id);
			ps.setString(2, vehicle_id);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				double speedFactor = result.getFloat("speedfactor");
				double time = result.getFloat("time");
				//Creating points on an speedByTimeChart.
				ChartPoint point = new ChartPoint(time, speedFactor);
				points.add(point);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Create a chart with the given points.
		chart = new Chart(points, "speedFactor");
		return chart; 
	}

	public Chart getRouteLengthByTimeChart(int simulation_id, String vehicle_id) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = "SELECT routes.time, ROUND((CAST(SUM(l.length) AS numeric)/1000), 4) as length\r\n" + 
				"FROM (	SELECT s.time, unnest(xpath('/snapshot/route/@edges', s.data))::text as edges, unnest(xpath('/snapshot/route/@id', s.data))::text as route_id \r\n" + 
				"		FROM projectschema.snapshot s \r\n" + 
				"		WHERE s.simulation = ?\r\n" + 
				"		ORDER BY s.time) as routes, (	SELECT s1.time, unnest(xpath('//vehicle/@route', s1.data))::text as routeId, unnest(xpath('//vehicle/@id', s1.data))::text as veh_id \r\n" + 
				"				  						FROM projectschema.snapshot s1 \r\n" + 
				"										WHERE s1.simulation = ?\r\n" + 
				"										ORDER BY s1.time) as vehicles, projectschema.lane l\r\n" + 
				"WHERE route_id = routeId\r\n" + 
				"AND vehicles.time = routes.time\r\n" + 
				"AND vehicles.veh_id = ?\r\n" + 
				"AND edges LIKE ('%' || REPLACE(l.lane_id, '_0', '') || ' %')\r\n" + 
				"GROUP BY routes.time";
		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<ChartPoint> points = new ArrayList<>();
		Chart chart = null;
		try {
			ps.setInt(1, simulation_id);
			ps.setInt(2, simulation_id);
			ps.setString(3, vehicle_id);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				double routeLength = result.getFloat("length");
				double time = result.getFloat("time");
				//Creating points on an routelength by time.
				ChartPoint point = new ChartPoint(time, routeLength);
				points.add(point);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Create a chart with the given points.
		chart = new Chart(points, "route length in km");
		return chart; 
	}
	
	
	private ArrayList<ChartPoint> getChartPoints(ResultSet result) throws SQLException {
		ArrayList<ChartPoint> points = new ArrayList<>();
		while(result.next()) {
			double y = Double.parseDouble(result.getString("speed"));
			double x = result.getFloat("time");
			ChartPoint point = new ChartPoint(x, y);
			points.add(point);
		}
		return points;
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
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
				//Create a chart with the given points.
		Chart chart = new Chart(points, laneId);
		return chart; 
		
	}
}
