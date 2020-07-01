package resources;

import dao.InitDAO;
import model.Database;
import model.InitResp;
import org.json.JSONObject;

import javax.ws.rs.*;
import java.sql.*;
import java.util.Properties;

@Path("/initialize_db")
public class InitializationResource {

       /**
        * Attempts to create a database.
        * See InitDAO.databaseInit() for more detail.
        * If a database with a "projectschema" schema already exists then it does not do anything.
        */
       @POST
       @Consumes("application/x-www-form-urlencoded")
       @Produces("application/json")
       public InitResp receiveInformation(@FormParam("database_url") String dbUrl, @FormParam("database_username") String dbUsername, @FormParam("database_password") String password) {
             InitResp response = InitDAO.instance.databaseInit(dbUrl, dbUsername, password);
             return response;
       }



   }

