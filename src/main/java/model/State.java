package model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class State {
	int id;
	float time;
	String data;
	
	public State() {} 
	
	public State(int id, float time, String data) {
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
	
	public String getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}


}

