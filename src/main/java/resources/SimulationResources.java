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
	public Response addSimulation(InputStream stream){
		try {
			SimulationDAO.instance.addSimulation(stream);
			return Response.ok().entity("").build();
		} catch (Exception e) {
			// e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
	
}
