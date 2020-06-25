package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.json.JSONObject;

import dao.SimulationDAO;

@Path("/simulations/metadata")
public class Metadata {
	/**
	 * update simulation metadata 
	 * @throws ParseException */
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String updateMetadata(String metaData, @Context HttpServletResponse httpResponse) throws ParseException {
		JSONObject metadataJson = new JSONObject(metaData);
		int simulationId = metadataJson.getInt("title");
		String name = metadataJson.getString("simulation_name");
		
		
		String dateStr = metadataJson.getString("date");
		System.out.println("datastring: "+dateStr);
		java.util.Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(dateStr!=null) {		
		    date = sdf.parse(dateStr);
		}
		
		String tag = metadataJson.getString("tag_values");
		String description = metadataJson.getString("description");
		
		String printout = "date: " +date+"/n"+ "description: "+description;
		System.out.println(printout);
		
	  /*dummy data in the database:
	   * insert into projectschema.simulation values(1,'Jane',TO_DATE('09/06/2020', 'DD/MM/YYYY'),'congestion','testUpdate')
	   */
		int updateResult = SimulationDAO.instance.editMetadata(simulationId, name, date, tag, description);
		JSONObject response = new JSONObject();
		if (updateResult == 1){
			response.put("result", "true");
		}
		else {
			
			response.put("result","false");
			
		}
		return response.toString();
	}

}
