package resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
<<<<<<< Updated upstream
import javax.ws.rs.QueryParam;

import org.json.JSONObject;

import dao.ChartDAO;
import model.EdgeAppearance;


=======
>>>>>>> Stashed changes

import org.json.JSONObject;

import dao.ChartDAO;

@Path("/simulations/{simulation_id}/charts")
public class ChartResources {
	
	@PathParam("simulation_id") int simulation_id;
	
	
	@GET
	@Produces("application/json")
	@Path("/transiting_vehicles")
	public JSONObject getTransiting_vehicles() {
		JSONObject transiting_vehicles = ChartDAO.instance.getTransitingVehicles(simulation_id);
		
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
