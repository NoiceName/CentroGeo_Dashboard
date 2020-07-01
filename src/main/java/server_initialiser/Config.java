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
        Database.readInParametersFromStorage();
    }

    /**
     * Executed upon server shutdown
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }




}
