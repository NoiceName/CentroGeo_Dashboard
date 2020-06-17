package resources;

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
	public JSONObject getVehicleInformation() {
		return new JSONObject();
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
}
