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
	 * Y-axis
	 */
	double y;

	public ChartPoint() {}

	public ChartPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public String toString() {
		return String.format("(%f,%f)", x, y);
	}

}
