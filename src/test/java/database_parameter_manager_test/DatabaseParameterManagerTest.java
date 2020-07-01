package database_parameter_manager_test;

import database_parameter_manager.DatabaseParameterManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the DataBaseParameterManager class
 */
public class DatabaseParameterManagerTest {

    //Todo delete this test later
    /**
     * Check whether the program is able to test if a file exists
     */
//    @Test
//    public void readTest(){
//        System.out.println(DatabaseParameterManager.readParameters());
//        assertTrue(true);
//    }


    /**
     * Test to be able to write and read from a file
     */
    @Test
    public void testWriteAndReadLogin(){
        DatabaseParameterManager.writeParameters("max", "password", "www.world.com/");
        JSONArray userArr = DatabaseParameterManager.readParameters();
        JSONObject userObject = (JSONObject)userArr.get(0);
        String password = (String)userObject.get("password");
        String username = (String)userObject.get("username");
        String url = (String)userObject.get("url");
        assertEquals(password, "password");
        assertEquals(username, "max");
        assertEquals(url, "www.world.com/");
    }

    /**
     * Test to be able to overwrite the data inside the file
     */
    @Test
    public void OverWriteFile(){
        DatabaseParameterManager.writeParameters("max", "password", "www.world.com/");
        DatabaseParameterManager.writeParameters("admin", "adminpass", "differentURL");
        JSONArray userArr = DatabaseParameterManager.readParameters();
        JSONObject userObject = (JSONObject)userArr.get(0);
        String password = (String)userObject.get("password");
        String username = (String)userObject.get("username");
        String url = (String)userObject.get("url");
        assertEquals(password, "adminpass");
        assertEquals(username, "admin");
        assertEquals(url, "differentURL");
    }



}
