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
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import model.Database;
import model.Snapshot;

public enum SnapshotDAO {
	instance;
	
	/**
	 * Idk wtf is going on here
	 * @param simulation_id
	 * @return
	 */
	public ArrayList<Snapshot> getSnapshotDumpXML(int simulation_id) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = ("select s.data, s.time, s.snapshot_id from \"projectschema\".snapshot s where s.simulation = ?");
		PreparedStatement st = db.prepareStatement(statement);
		ArrayList<Snapshot> snapshots = new ArrayList<>();
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
				Snapshot newSnapshot = new Snapshot();
				String xml = result.getString("data");
				float time = result.getFloat("time");
				int id = result.getInt("snapshot_id");
				if (xml == null) {
					break;
				}
				xml.replaceAll("\\\\n", "");
				newSnapshot.setData(xml);
				newSnapshot.setTime(time);
				newSnapshot.setId(id);
				snapshots.add(newSnapshot);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return snapshots;
	}

	/**
	 * In case we need to use JSON instead for the whole thing but Sam doesnt like it >:0
	 * @param simulation_id
	 * @return
	 */
	public JSONObject getStateDUMPJSON(int simulation_id) {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
		String statement = ("select s.snapshot_id, s.data from \"projectschema\".snapshot s where s.simulation = ?");
		PreparedStatement st = db.prepareStatement(statement);
		JSONObject resultObject = new JSONObject();
		try {
			st.setInt(1, simulation_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet result = null;
		try {
			result = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(result.next()) {
				Snapshot newSnapshot = new Snapshot();
				if(result.getString("data")==null) {
					break;
				}
				JSONObject xml = XML.toJSONObject(result.getString("data"));
				resultObject.append(result.getString("snapshot_id"), xml);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultObject;
	}
	
	/**
	 * Sam doesnt like server processing
	 * @param simulation_id
	 * @param lane_id
	 * @return
	 */
	public ArrayList<Snapshot> getAllSnapshotsForOneLane(int simulation_id, String lane_id){
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
		return new ArrayList<Snapshot>();
	}

}