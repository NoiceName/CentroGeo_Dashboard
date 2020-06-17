package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A single point on a EdgeAppereanceChart
 * @author Maksym 
 *
 */
@XmlRootElement
public class EdgeAppearancePoint {
	
	/**
	 * Time of a particular snapshot
	 */
	double time;
	
	/**
	 * Number of times a node has occured given the time.
	 */
	int count;

	public EdgeAppearancePoint() {}

	public EdgeAppearancePoint(double time, int count) {
		this.time = time;
		this.count = count;
	}
	
	public double getTime() {
		return this.time;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setTime(double time) {
		this.time = time;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString() {
		return String.format("(%f,%d)", time, count);
	}

}
