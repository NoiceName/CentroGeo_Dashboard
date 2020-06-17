package dao;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		return new Chart();
	}
	
	public Chart getSpeedFactorByTimeChart(int simulation_id, String vehicle_id) {
		return new Chart();
	}

	public Chart getRouteLengthByTimeChart(int simulation_id, String vehicle_id) {
		return new Chart();
	}
}
