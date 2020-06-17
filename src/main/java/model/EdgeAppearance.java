package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

/**
 * An Edge appearance frequency chart
 * @author Maksym
 */
@XmlRootElement
public class EdgeAppearance {
	
	/**
	 * Data used to generate the chart
	 */
	private ArrayList<JSONObject> data;
	
	/**
	 * ID of the edge
	 */
	private String id;
	
	public EdgeAppearance() {}
	
	public EdgeAppearance(ArrayList<JSONObject> data, String id) {
		this.data = data;
		this.id = id;
	}

	public ArrayList<JSONObject> getData() {
		return this.data;
	}
	
	public void setData(ArrayList<JSONObject> data) {
		this.data = data;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String toString() {
		return new String("ID: " + this.id + "\n" + "Data: " + this.data + "\n");
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("data", this.data);
		return json;
	}

	
}
