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
      JSONObject resp = new JSONObject();
      try {
         con = DriverManager.getConnection(dbUrl, props);
         con.close();
      } catch (SQLException e) {
            resp.put("result", "bad_login");
            return resp;
      }
      Database.setUrl(dbUrl);
      Database.setPassword(password);
      Database.setUsername(dbUsername);
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
          resp.put("result", "bad_exists");
      } else {
          PreparedStatement schemaStatement = db.prepareStatement("drop schema if exists \"projectschema\" cascade;\n" +
                  "\n" +
                  "create schema \"projectschema\";\n" +
                  "\n" +
                  "create table \"projectschema\".simulation(\n" +
                  "                                           simulation_id serial primary key,\n" +
                  "                                           name text,\n" +
                  "                                           date date,\n" +
                  "                                           tags text,\n" +
                  "                                           description text\n" +
                  ");\n" +
                  "\n" +
                  "create table \"projectschema\".snapshot(\n" +
                  "                                         snapshot_id serial primary key,\n" +
                  "                                         simulation integer not null\n" +
                  "                                             references \"projectschema\".simulation (simulation_id) on delete cascade,\n" +
                  "                                         time real,\n" +
                  "                                         data xml\n" +
                  ");\n" +
                  "\n" +
                  "create table \"projectschema\".lane(\n" +
                  "                                     lane_id text,\n" +
                  "                                     edge_id text,\n" +
                  "                                     simulation integer not null\n" +
                  "                                         references \"projectschema\".simulation (simulation_id) on delete cascade,\n" +
                  "                                     index integer,\n" +
                  "                                     length real,\n" +
                  "                                     shape text,\n" +
                  "                                     primary key (lane_id, simulation)\n" +
                  ");\n" +
                  "\n" +
                  "create table \"projectschema\".snapshotlane(\n" +
                  "                                             snapshot_lane_id serial primary key,\n" +
                  "--                                              lane text not null\n" +
                  "--                                                  references \"projectschema\".lane (lane_id) on delete cascade,\n" +
                  "                                             snapshot integer not null\n" +
                  "                                                 references \"projectschema\".snapshot (snapshot_id) on delete cascade\n" +
                  ");\n" +
                  "\n" +
                  "\n" +
                  "create table \"projectschema\".user(\n" +
                  "\n" +
                  "                                     username text primary key,\n" +
                  "                                     password_hash text\n" +
                  "\n" +
                  ");\n" +
                  "\n" +
                  "\n" +
                  "create table \"projectschema\".vehicle(\n" +
                  "                                        vehicle_id text,\n" +
                  "                                        simulation integer not null\n" +
                  "                                            references \"projectschema\".simulation (simulation_id) on delete cascade,\n" +
                  "                                        depart real,\n" +
                  "                                        primary key (vehicle_id, simulation)\n" +
                  ");\n" +
                  "\n" +
                  "create table \"projectschema\".snapshotvehicle(\n" +
                  "                                                snapshot_vehicle_id serial primary key,\n" +
                  "--                                                 vehicle text constraint vehicle_fk not null\n" +
                  "--                                                     references \"projectschema\".vehicle (vehicle_id, simulation) on delete cascade,\n" +
                  "                                                snapshot integer constraint snapshot_fk not null\n" +
                  "                                                    references \"projectschema\".snapshot (snapshot_id) on delete cascade,\n" +
                  "                                                snapshot_lane integer not null\n" +
                  "                                                    references \"projectschema\".snapshotlane (snapshot_lane_id) on delete cascade,\n" +
                  "                                                simulation integer not null\n" +
                  "                                                    references \"projectschema\".simulation (simulation_id) on delete cascade,\n" +
                  "                                                type text,\n" +
                  "                                                speedfactor real,\n" +
                  "                                                state text,\n" +
                  "                                                pos float,\n" +
                  "                                                speed float,\n" +
                  "                                                poslat float\n" +
                  ");\n");
         try {
            schemaStatement.executeQuery();
         } catch (SQLException throwables) {
             System.out.println(throwables.getMessage());
         }
      }

      resp.put("result", "success");
      return resp;
   }



   }

