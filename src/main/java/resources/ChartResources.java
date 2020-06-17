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
import model.EdgeAppearance;



import org.json.JSONObject;

@Path("/simulations/{simulation_id}/charts")
public class ChartResources {
	
	@PathParam("simulation_id") int simulation_id;
	
	@GET
	@Path("/transiting_vehicles")
	public JSONObject getTransiting_vehicles() {
		return new JSONObject();
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
	public ArrayList<EdgeAppearance> getEdgeAppearance(@QueryParam("edge_ids") List<String> edgeIds){
		ArrayList<EdgeAppearance> charts = new ArrayList<>();
		for(String edge : edgeIds) {
			EdgeAppearance chart = ChartDAO.instance.getEdgeAppereance(simulation_id, edge);
			charts.add(chart);
		}
		return charts;
	}
}
