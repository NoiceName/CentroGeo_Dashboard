package resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import dao.SnapshotDAO;
import model.Snapshot;

@Path("/simulations/{simulation_id}/snapshots")
public class SnapshotResources {

	/**
	 * The id of the simulation to which the snapshots belong
	 */
	@PathParam("simulation_id") int simulation_id;

	/**
	 * Retrieves and returns all snapshots of the requested simulation
	 * @return
	 */
	@GET
	@Produces("application/json")
	public ArrayList<Snapshot> getSnapshots() {
		ArrayList<Snapshot> xml = SnapshotDAO.instance.getSnapshotDumpXML(simulation_id);
		return xml;
	}	

}
