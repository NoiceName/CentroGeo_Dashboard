package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Simulation {
	
	int simulationId;
	String text;
	String date;
	String tags;
	String description;
	
	
	
	public void setText(String text) {
		this.text = text;
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
	
	public String getDescripition() {
		return this.description;
	}
	
	public String getTags() {
		return this.tags;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getText() {
		return this.text;
	}

	public int getSimulationId() {
		return this.simulationId;
	}
	
}
