package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import dao.StateDAO;

@Path("/metadata")
public class MetadataResource {
	
	
	/**
	 * AJAX
	 * @param json
	 * @return
	 */
	@POST
	@Path("/states")
	@Consumes("application/json")
	@Produces("text/xml")
	public String getStates(String json) {
		JSONObject stateRequest = new JSONObject(json);
		String simulation = stateRequest.getString("simulation");
		int sim_id = Integer.parseInt(simulation);
		String xml = StateDAO.instance.getStateDump(sim_id);
		return xml;
	}
}
