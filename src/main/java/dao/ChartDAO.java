package dao;

import java.sql.Array;
import java.sql.Connection;
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
  
	
	/**
     * Generate Cumulative Number of Arrived Vehicles chart
     * @param simulationId - Id of a simulation
     * @return chart object
     * @throws SQLException
     */
	public Chart getVehicleNumber(int simulationId) throws SQLException {
		Database db = new Database(); 
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement= "SELECT s.time, unnest(xpath('//delay/@end', s.data))::text as number " + 
    			"FROM projectschema.snapshot s " + 
    			"WHERE s.simulation = ?  " + 
    			"ORDER BY time ASC";
		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<ChartPoint> points = new ArrayList<>(); 
		
	    try {
	    	ps.setInt(1, simulationId);
	    	ResultSet result = ps.executeQuery();
	    	while(result.next()) {
				double time = result.getFloat("time");
				double number = Double.parseDouble(result.getString("number"));
				//create points on chart of Cumulative Number of Arrived Vehicles
				ChartPoint point = new ChartPoint(time, number);
				points.add(point);								
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	    finally {
	    	ps.close();
	    }
			Chart chart = new Chart(points, "Simulation " + Integer.toString(simulationId));
		    return chart;
		    
	}


	/**
     * Generate Number of running vehicles chart
     * @param simulationId - Id of a simulation
     * @return chart object
     * @throws SQLException
     */
	public Chart getRunVehicles(int simulationId) {
		Database db = new Database(); 
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement= "SELECT s.time, unnest(xpath('//delay/@number', s.data))::text as number " + 
    			"FROM projectschema.snapshot s " + 
    			"WHERE s.simulation = ?  " + 
    			"ORDER BY time ASC";
		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<ChartPoint> points = new ArrayList<>(); 
		
	    try {
	    	ps.setInt(1, simulationId);
	    	ResultSet result = ps.executeQuery();
	    	while(result.next()) {
				double time = result.getFloat("time");
				double number = Double.parseDouble(result.getString("number"));
				//create points on chart of Cumulative Number of Arrived Vehicles
				ChartPoint point = new ChartPoint(time, number);
				points.add(point);								
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
			Chart chart = new Chart(points, "Simulation " + Integer.toString(simulationId));
		    return chart;
		    
	}
	
	public Chart getAverageSpeed(int simulationId) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		// to omit the cars with a null speed, remove the SQL comment bfore the "WHERE speed > 0"
		String statement = "SELECT time, ROUND(AVG(speed), 2) AS AVGspeed\r\n" + 
				"FROM (	SELECT s.time, CAST(unnest(xpath('//vehicle/@speed', s.data))::text AS numeric) AS speed, unnest(xpath('//vehicle/@id', s.data))::text AS veh_id \r\n" + 
				"		FROM projectschema.snapshot s\r\n" + 
				"		WHERE s.simulation = ?\r\n" + 
				"		ORDER BY s.time) AS vehicles\r\n" + 
				"-- -WHERE speed > 0\r\n" + 
				"GROUP BY time";
		PreparedStatement ps = db.prepareStatement(statement);
		ArrayList<ChartPoint> points = new ArrayList<>();
		Chart chart = null;
		try {
			ps.setInt(1, simulationId);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				double speed = result.getFloat("avgSpeed");
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
		chart = new Chart(points, "AVG speed in m/s");
		return chart; 
	}

}
