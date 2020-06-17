package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

/**
 * Edge Appearance Frequency Chart
 * @author Maksym
 */
@XmlRootElement
public class EdgeAppearance {
	
	/**
	 * Points on the chart
	 */
	private ArrayList<EdgeAppearancePoint> data;
	
	/**
	 * ID of the edge
	 */
	private String id;
	
	public EdgeAppearance() {}
	
	public EdgeAppearance(ArrayList<EdgeAppearancePoint> data, String id) {
		this.data = data;
		this.id = id;
	}

	public ArrayList<EdgeAppearancePoint> getData() {
		return this.data;
	}
	
	public void setData(ArrayList<EdgeAppearancePoint> data) {
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
