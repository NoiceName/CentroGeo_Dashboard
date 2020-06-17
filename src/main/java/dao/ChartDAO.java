package dao;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import model.EdgeAppearance;
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
	 * Generates a JSONObject
	 * @param simulationId - ID of a simulation
	 * @param edgeId - Id of the specified edge 
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
		ArrayList<JSONObject> res = new ArrayList<>();
		try {
			ps.setInt(1, simulationId);
			ps.setString(2, edgeId);
			ps.setString(3, edgeId);
			ps.setString(4, edgeId);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				int count = result.getInt("counter");
				String time = String.valueOf(result.getInt("time"));
				JSONObject json = new JSONObject();
				json.put(time, count);
				res.add(json);
			}
			System.out.println(res);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		EdgeAppearance chart = new EdgeAppearance(res, edgeId);
		return chart; 
	}
	
}
