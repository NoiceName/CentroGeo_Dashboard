package dao;

import java.sql.PreparedStatement;

import org.json.JSONObject;

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
}
