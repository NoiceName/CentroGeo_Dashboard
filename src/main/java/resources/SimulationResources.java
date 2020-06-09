package resources;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import cookie_manager.CookieManager;
import dao.SimulationDAO;
import dao.SnapshotDAO;
import dao.UserDAO;
import model.Database;
import model.Snapshot;
import model.User;

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
	
	/**
	 * update simulation metadata 
	 * @throws ParseException */
	@POST
	@Path("/metadata")
	@Consumes("application/json")
	@Produces("application/json")
	public String updateMetadata(String metaData, @Context HttpServletResponse httpResponse) throws ParseException {
		JSONObject metadataJson = new JSONObject(metaData);
		int simulationId = metadataJson.getInt("title");
		String name = metadataJson.getString("editor");
				
		String dateStr = metadataJson.getString("date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = sdf.parse(dateStr);
		
		String tag = metadataJson.getString("tag");
		String description = metadataJson.getString("description");
		
	  //insert into projectschema.simulation values(1,'Jane',TO_DATE('09/06/2020', 'DD/MM/YYYY'),'congestion','testUpdate')
	
		int updateResult = SimulationDAO.instance.editMetadata(simulationId, name, date, tag, description);
		JSONObject response = new JSONObject();
		if (updateResult == 0){
			response.put("result", "false");
		}
		else if(updateResult == 1){
			
			response.put("result","true");
			
		}
		return response.toString();
	}
	
}
