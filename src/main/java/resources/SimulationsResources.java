package resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import dao.SnapshotDAO;
import model.Snapshot;

@Path("/metadata")
public class SimulationsResources {
	
	
//just a test	
	@GET
	public String getSomething() {
		return "Hello!";
	}
	
	/**
	 * JSON implementation 
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("/states")
//	@Produces("application/json")
//	@Consumes("application/json")
//	public String getStates(String json) {
//		System.out.println("received request");
//		JSONObject stateRequest = new JSONObject(json);
//		int simulation = stateRequest.getInt("simulation");
//		JSONObject jsonResponse = StateDAO.instance.getStateDUMPJSON(simulation);
//		System.out.println();
//		return jsonResponse.toString();
//	}
	
	@POST
	@Path("/states")
	@Produces("application/json")
	@Consumes("application/json")
	public ArrayList<Snapshot> getSnapshots(String json) {
		System.out.println("received request");
		JSONObject stateRequest = new JSONObject(json);
		int simulation = stateRequest.getInt("simulation");
		ArrayList<Snapshot> xml = SnapshotDAO.instance.getSnapshotDumpXML(simulation);
		return xml;
	}	
	
}
