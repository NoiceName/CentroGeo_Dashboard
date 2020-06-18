package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Simulation {
	
	int simulationId;
	String name;
	String date;
	String tags;
	String description;
	
	
	public Simulation() {}
	
	public Simulation(int simulationId, String name, String date, String tags, String description) {
		this.name = name;
		this.simulationId = simulationId;
		this.date = date;
		this.tags = tags;
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void  setSimulationId(int simulationId) {
		this.simulationId = simulationId;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getTags() {
		return this.tags;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getName() {
		return this.name;
	}

	public int getSimulationId() {
		return this.simulationId;
	}

	public String toString() {
		return String.format("(%d, %s, %s, %s, %s)", simulationId, name, date, tags, description);
	}
	
}
