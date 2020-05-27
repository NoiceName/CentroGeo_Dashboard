package database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import model.Database;

class StateDAOTest {

	
	@Test
	void getStatesTest() {
		Database db = new Database();
		Database.loadPGSQL();
		db.connectPGSQL();
//		PreparedStatement statement = db.prepareStatement("insert into \"" + db.getSchema() + "\".simulation (name, date, tags, description) values ('simulationOne', '(12,12,12)', 'some tags', 'other infor')");
		PreparedStatement statement1 = db.prepareStatement("insert into \"" + db.getSchema() + "\".snapshot (time, simulation) values ('20.0', '1')");
//		PreparedStatement st1 = db.prepareStatement("insert into \"" + db.getSchema() + "\".lane (lane_id) values (':n5_0_0')");
		PreparedStatement st2 = db.prepareStatement("insert into \"" + db.getSchema() + "\".snapshotlane (lane, snapshot) values (':n5_0_0', '2')");
//		PreparedStatement st3 = db.prepareStatement("insert into \"" + db.getSchema() + "\".vehicle (vehicle_id) values ('v0')");
		PreparedStatement st4 = db.prepareStatement("insert into \"" + db.getSchema() + "\".snapshotvehicle (snapshot, snapshot_lane, vehicle) values ('2', '2', 'v0')");
		try {
//			statement.execute();
			statement1.execute();
//			st1.execute();
			st2.execute();
//			st3.execute();
			st4.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
