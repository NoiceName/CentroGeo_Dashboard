package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class State {
	int id;
	float time;
	ArrayList<LaneSnapshot> lanes;
	
	State() {} 
	
	public State(int id, float time) {
		this.time = time;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	
	public float getTime() {
		return this.time;
	}
	
	public ArrayList<LaneSnapshot> getLanes(){
		return this.lanes;
	}
	
	public void setLanes() {
		
	}
}

