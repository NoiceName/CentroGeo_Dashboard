package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A single point on a EdgeAppereanceChart
 * @author Maksym 
 *
 */
@XmlRootElement
public class ChartPoint {
	
	/**
	 * Time of a particular snapshot
	 */
	double x;
	
	/**
	 * Number of times a node has occured given the x.
	 */
	double y;

	public ChartPoint() {}

	public ChartPoint(double time, int count) {
		this.x = time;
		this.y = count;
	}
	
	public double getTime() {
		return this.x;
	}
	
	public double getCount() {
		return this.y;
	}
	
	public void setTime(double time) {
		this.x = time;
	}
	
	public void setCount(int count) {
		this.y = count;
	}
	
	public String toString() {
		return String.format("(%f,%d)", x, y);
	}

}
