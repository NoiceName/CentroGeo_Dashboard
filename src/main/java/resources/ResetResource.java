package resources;



import cookie_manager.Secured;
import database_parameter_manager.DatabaseParameterManager;
import model.Database;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/reset_db")
public class ResetResource {

    /**
     * Resets the database parameters
     */
    @POST
    @Secured
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response resetDatabase(@FormParam("action") String action){
        JSONObject resp = new JSONObject();
        System.out.println(action);
        if(!(action.equals("RESET"))){
            resp.put("result", "false");
            return Response.ok().entity(resp.toString()).build();
        } else {
            Database.setParameters("", "", "");
            System.out.println("Database disconnected...");
            resp.put("result", "true");
            return Response.ok().entity(resp.toString()).build();
        }
    }



}

