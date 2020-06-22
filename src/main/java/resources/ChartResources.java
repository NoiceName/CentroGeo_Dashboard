package resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.json.JSONObject;

import dao.ChartDAO;
import model.Chart;

@Path("/simulations/{simulation_id}/charts")
public class ChartResources {
	
	@PathParam("simulation_id") int simulation_id;
	
	
	@GET
	@Produces("application/json")
	@Path("/transiting_vehicles")
	public ArrayList<Chart> getTransiting_vehicles(@QueryParam("lanes_ids") List<String> laneIds) {
		ArrayList<Chart> charts = new ArrayList<>();
		for(String lane : laneIds) {
			Chart chart = ChartDAO.instance.getTransiting_vehicles(simulation_id, lane);
			charts.add(chart);
		}
		
		return charts;
	}

	
	
	@GET
	@Path("/vehicle_information")
	@Produces("application/json")
	public ArrayList<Chart> getVehicleInformation(@QueryParam("vehicle_id") String vehicle_id) {
		ArrayList<Chart> charts = new ArrayList<>();
			Chart speedChart = ChartDAO.instance.getSpeedByTimeChart(simulation_id, vehicle_id);
			charts.add(speedChart);
			Chart speedFactorChart = ChartDAO.instance.getSpeedFactorByTimeChart(simulation_id, vehicle_id);
			charts.add(speedFactorChart);
			Chart routeLengthChart = ChartDAO.instance.getRouteLengthByTimeChart(simulation_id, vehicle_id);
			charts.add(routeLengthChart);
		return charts;
	}

	/**
	 * @param edgeIds - An array of desired ID's
	 * @return An array of Edge Appearance Frequency charts for each edge id.
	 * */
	@GET
	@Path("/edge_appearance")
	@Produces("application/json")
	public ArrayList<Chart> getEdgeAppearance(@QueryParam("edge_ids") List<String> edgeIds){
		ArrayList<Chart> charts = new ArrayList<>();
		for(String edge : edgeIds) {
			Chart chart = ChartDAO.instance.getEdgeAppereance(simulation_id, edge);
			charts.add(chart);
		}
		return charts;
	}
	
	@GET
	@Path("/cumulative_number_of_arrived_vehicles")
	@Produces("application/json")
	public Chart getArrivedVehicles() throws SQLException {
		
		return ChartDAO.instance.getVehicleNumber(simulation_id);
	}
	
	@GET
	@Path("/number_of_running_vehicles")
	@Produces("application/json")
	public Chart getRunningVehicles() throws SQLException {
		
		return ChartDAO.instance.getRunVehicles(simulation_id);
	}
	
	@GET
	@Path("/average_vehicle_speed")
	@Produces("application/json")
	public Chart getAVGSpeed() throws SQLException {
		
		return ChartDAO.instance.getAverageSpeed(simulation_id);
	}
}
