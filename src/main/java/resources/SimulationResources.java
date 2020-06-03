package resources;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
	@Consumes("application/zip")
	public void getZip(InputStream stream){
		Database.loadPGSQL();
		Database newDb = new Database();

		try {
			newDb.connectPGSQL();
			Connection connection = newDb.getConnection();
			System.out.println("Downloaded file");
			extraction.ZipExtraction.getZipData(stream, connection);
			System.out.println("File added to database");
		} catch (IOException e) {
			e.printStackTrace(); }
	}	
	
}
