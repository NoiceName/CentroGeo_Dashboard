package dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.json.JSONObject;

import model.Database;
import model.State;

public enum StateDAO {
	instance;

	
	public ArrayList<State> getAllStates(int simulation_id) {
		return new ArrayList();
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
