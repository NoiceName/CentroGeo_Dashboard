package resources;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.crypto.Data;

import cookie_manager.Secured;
import dao.SimulationDAO;
import model.Simulation;
import org.json.JSONObject;

import dao.SnapshotDAO;
import model.Database;
import model.Snapshot;

@Path("/simulations")
public class SimulationResources {
	
	/**
	 * Uploads the simulation file into the database
	 * @param stream
	 */
	@POST
	@Secured
	@Consumes("application/zip")
	public void addSimulation(InputStream stream){
		SimulationDAO.instance.addSimulation(stream);
	}

	@DELETE
	@Secured
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void deleteSimulation(@FormParam("id") String name){
		System.out.println(name);
		SimulationDAO.instance.deleteSimulation(name);
	}
	
}
