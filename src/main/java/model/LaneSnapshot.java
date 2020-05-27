package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LaneSnapshot {
	
	String lane_id;
	int vehicleNumber;
	
	LaneSnapshot() {}
	
	public LaneSnapshot(int vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public int getVehicleCount() {
		return this.vehicleNumber;
	}
	
	public void setVehicleCount(int vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	public void setLaneId(String lane_id) {
		this.lane_id = lane_id;
	}
	
	public String getLaneId() {
		return this.lane_id;
	}

}
