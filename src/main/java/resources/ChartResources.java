package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

@Path("/simulations/{simulation_id}/charts")
public class ChartResources {
	
	@PathParam("simulation_id") int simulation_id;

	
	
	@GET
	@Path("/vehicle_information")
	public JSONObject getVehicleInformation() {
		return new JSONObject();
	}
	
}
