package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

/**
 * Edge Appearance Frequency Chart
 * @author Maksym
 */
@XmlRootElement
public class Chart {
	
	/**
	 * Points on the chart
	 */
	private ArrayList<ChartPoint> data;
	
	/**
	 * ID of the option 
	 * */
	private String id;
	
	public Chart() {}
	
	public Chart(ArrayList<ChartPoint> data, String id) {
		this.data = data;
		this.id = id;
	}

	public ArrayList<ChartPoint> getData() {
		return this.data;
	}
	
	public void setData(ArrayList<ChartPoint> data) {
		this.data = data;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String toString() {
		return new String("ID: " + this.id + "\n" + "Data: " + this.data.toString() + "\n");
	}

}
