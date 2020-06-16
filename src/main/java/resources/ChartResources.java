package resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/simulations/{simulation_id}/charts")
public class ChartResources {
	
	@PathParam("simulation_id") int simulation_id;


}
