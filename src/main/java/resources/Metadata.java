package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		String name = metadataJson.getString("editor");
				
		String dateStr = metadataJson.getString("date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = sdf.parse(dateStr);
		
		String tag = metadataJson.getString("tag");
		String description = metadataJson.getString("description");
		
	  /*dummy data in the database:
	   * insert into projectschema.simulation values(1,'Jane',TO_DATE('09/06/2020', 'DD/MM/YYYY'),'congestion','testUpdate')
	   */
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
