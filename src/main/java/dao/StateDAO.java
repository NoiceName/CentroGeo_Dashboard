package dao;

import java.io.IOException;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import model.Database;
import model.State;

public enum StateDAO {
	instance;
	
	/**
	 * Idk wtf is going on here
	 * @param simulation_id
	 * @return
	 */
	public ArrayList<State> getStateDump(int simulation_id) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = ("select s.data from \"projectschema\".snapshot s where s.simulation = ?");
		PreparedStatement st = db.prepareStatement(statement);
		ArrayList<State> states = new ArrayList<>();
		try {
			st.setInt(1, simulation_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet result = null;
		try {
			result = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(result.next()) {
				State newState = new State();
				String xml = result.getString("data");
				if (xml == null) {
					break;
				}
				xml.replaceAll("\\\\n", "");
				newState.setData(xml);
				states.add(newState);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return states;
	}

	
	public ArrayList<State> getAllStatesForOneLane(int simulation_id, String lane_id){
		Database db = new Database();
		db.connectPGSQL();
		String statement = "select s.time, s.snapshot_id, sl.lane, count(sv.snapshot_vehicle_id)" 
				+ "from \"projectschema\".snapshot s, \"projectschema\".snapshotlane sl, \"projectschema\".snapshotvehicle sv"
				+ "where s.snapshot_id = ?"
				+ "and l.lane_id = ?"
				+ "l.lane_id = sl.lane"
				+ "and sl.snapshot = s.snapshot_id"
				+ "and sv.snapshot_lane = sl.snapshot_lane_id"
				+ "group by s.time, s.snapshot_id, sl.lane";
		PreparedStatement st = db.prepareStatement(statement);
		return new ArrayList<State>();
	}

}
