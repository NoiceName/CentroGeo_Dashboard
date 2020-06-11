package resources;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;



import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
	@Consumes("application/zip")
	public void addSimulation(InputStream stream){
		SimulationDAO.instance.addSimulation(stream);
	}

	
	
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void deleteSimulation(@FormParam("id") String name){
		System.out.println(name);
		SimulationDAO.instance.deleteSimulation(name);
	}
	
}
