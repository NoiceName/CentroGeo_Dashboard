package resources;

import model.Database;
import org.json.JSONObject;

import javax.ws.rs.*;
import java.sql.*;
import java.util.Properties;

@Path("/initialize_db")
public class InitializationResource {


   @POST
   @Consumes("application/x-www-form-urlencoded")
   public JSONObject receiveInformation(@FormParam("database_url") String dbUrl, @FormParam("database_username") String dbUsername, @FormParam("database_password") String password) {
      //Check if the given parameters are valid
       System.out.println("executed");
      Database.loadPGSQL();
      Properties props = new Properties();
      props.setProperty("user", dbUsername);
      props.setProperty("password", password);
      Connection con = null;
      try {
         con = DriverManager.getConnection(dbUrl, props);
         con.close();
      } catch (SQLException e) {
         //Return the credentials are invalid
      }
      //Set database credentials here!
      Database.setUrl(dbUrl);
      Database.setPassword(password);
      Database.setUsername(dbUsername);
      //Check if schema exists
      Database db = new Database();
      db.connectPGSQL();
      PreparedStatement st = db.prepareStatement("SELECT schema_name FROM information_schema.schemata WHERE schema_name = 'projectschema';");
      String schemaName = null;
      try {
         ResultSet rs = st.executeQuery();
         while(rs.next()) {
            schemaName = rs.getString("schema_name");
         }
      } catch (SQLException throwables) {
          System.out.println(throwables.getMessage());
      }

      PreparedStatement schemaSt;
      if(schemaName.equals("projectschema")){
         //Return projectschema already exists
      } else {
      }

      return null;

   }





//      public Connection connectPGSQL() {
//         Properties props = new Properties();
//         props.setProperty("user", this.username);
//         props.setProperty("password", this.password);
//         Connection conn = null;
//         try {
//            conn = DriverManager.getConnection(this.url, props);
//         } catch (SQLException e) {
//            e.printStackTrace();
//         }
//         this.connection = conn;
//         return conn;
//
//      }
   }

