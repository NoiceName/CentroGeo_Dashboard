package resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import dao.StateDAO;
import model.State;

@Path("/metadata")
public class MetadataResource {
	
	
//just a test	
	@GET
	public String getSomething() {
		return "Hello!";
	}
	
	/**
	 * AJAX
	 * @param json
	 * @return
	 */
	@POST
	@Path("/states")
	@Produces("application/json")
	@Consumes("application/json")
	public ArrayList<State> getStates(String json) {
		System.out.println("received request");
		JSONObject stateRequest = new JSONObject(json);
		int simulation = stateRequest.getInt("simulation");
		ArrayList<State> xml = StateDAO.instance.getStateDump(simulation);
		return xml;
	}
}
