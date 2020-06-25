package resources;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;

import cookie_manager.Secured;
import dao.SimulationDAO;
import extraction.ZipExtraction;
import model.Simulation;
import org.json.JSONObject;

import dao.SnapshotDAO;
import model.Database;

@Path("/simulations")
public class SimulationResources {
	
	/**
	 * Uploads the simulation file into the database
	 * @param stream
	 */
	@POST
	@Secured
	@Consumes("application/zip")
	@Produces("application/json")
	public Response addSimulation(InputStream stream){
		JSONObject response = new JSONObject();
		try {
			ZipExtraction.instance.extract(stream);
			response.put("result", "true");
			return Response.ok().entity(response.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			response.put("result", e.getMessage());
			return Response.serverError().entity(response.toString()).build();
		}
	}

	@GET
	@Produces("application/json")
	public ArrayList<Simulation> getSimulations(){
		ArrayList<Simulation> sims = SimulationDAO.instance.getSimulations();
		return sims;
	}
	
	@DELETE
	@Secured
	@Path("/{simulation_id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void deleteSimulation(@PathParam("simulation_id") int id){
		System.out.println("Deleting sim id: " + id);
		SimulationDAO.instance.deleteSimulation(id);
	}
	
	@GET
	@Path("/simulation_ids")
	@Produces("application/json")
	public ArrayList<Integer> getSimulationIds() {
		ArrayList<Integer> simIDs = SimulationDAO.instance.getSimulationIds();
		return simIDs;
	}
	
	@GET
	@Path("/{simulation_id}/lane_ids")
	@Produces("application/json")
	public ArrayList<String> getLaneIds(@PathParam("simulation_id") int id) {
		ArrayList<String> laneIDs = SimulationDAO.instance.getLaneIds(id);
		return laneIDs;
	}
	
	@GET
	@Path("/{simulation_id}/edge_ids")
	@Produces("application/json")
	public ArrayList<String> getEdgeIds(@PathParam("simulation_id") int id) {
		ArrayList<String> edgeIDs = SimulationDAO.instance.getEdgeIds(id);
		return edgeIDs;
	}
	
	@GET
	@Path("/{simulation_id}/vehicle_ids")
	@Produces("application/json")
	public ArrayList<String> getVehicleIds(@PathParam("simulation_id") int id) {
		ArrayList<String> vehicleIDs = SimulationDAO.instance.getVehicleIds(id);
		return vehicleIDs;
	}
}
