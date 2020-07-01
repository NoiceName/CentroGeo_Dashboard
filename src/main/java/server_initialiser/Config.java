package server_initialiser;

import database_parameter_manager.DatabaseParameterManager;
import model.Database;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Config implements ServletContextListener {

    /**
     * Executed upon server startup used for setting db parameters
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        readDatabaseParameters();
    }

    /**
     * Executed upon server shutdown
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    /**
     * Reads the database parameters from the file into the Database class
     */
    private void readDatabaseParameters() {
        JSONArray arr = DatabaseParameterManager.readParameters();
        if(arr.equals(new JSONArray())){
            System.out.println("The parameter file is empty not setting parameters");
            Database.setUsername("");
            Database.setUrl("");
            Database.setPassword("");
        } else {
            System.out.println("Setting database parameters from storage");
            JSONObject params = (JSONObject)arr.get(0);
            Database.setUsername((String)params.get("username"));
            Database.setUrl((String)params.get("url"));
            Database.setPassword((String)params.get("password"));
        }

    }

}
