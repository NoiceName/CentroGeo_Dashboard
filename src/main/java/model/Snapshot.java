package model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Snapshot {
	/**
	 * ID of the snapshot in the database
	 */
	int snapshot_id;
	
	/**
	 * The time of the snapshot
	 */
	float time;

	/**
	 * The full XML of the snapshot
	 */
	String data;
	
	public Snapshot() {} 
	
	public Snapshot(int id, float time, String data) {
		this.time = time;
		this.snapshot_id = id;
	}
	
	public int getId() {
		return this.snapshot_id;
	}
	
	public void setId(int id) {
		this.snapshot_id = id;
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	
	public float getTime() {
		return this.time;
	}
	
	public String getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}


}

