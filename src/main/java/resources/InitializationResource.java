package resources;

import model.Database;

import javax.ws.rs.*;

@Path("/initialize_db")
public class InitializationResource {


   @POST
   @Consumes("application/x-www-form-urlencoded")
   public void receiveInformation(@FormParam("database_url") String dbUrl, @FormParam("database_username") String dbUsername, @FormParam("database_password") String password){
      //Check if the given parameters are valid
      Database.loadPGSQL();

   }

}
