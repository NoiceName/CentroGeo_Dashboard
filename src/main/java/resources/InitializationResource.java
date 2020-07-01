package resources;

import dao.InitDAO;
import dao.UserDAO;
import model.Database;
import model.InitResp;
import model.User;
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
             if(response.getResult().equals("success")){
                 User admin = new User("admin", "123456");
                 try {
                     UserDAO.instance.insertUser(admin);
                 } catch (Exception e) {
                     System.out.println("Unable to create an administrator");
                     System.out.println(e.getMessage());
                     e.printStackTrace();
                 }
             }
             return response;
       }



   }

