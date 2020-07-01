package database_parameter_manager_test;

import database_parameter_manager.DatabaseParameterManager;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the DataBaseParameterManager class
 */
public class DatabaseParameterManagerTest {

    /**
     * Test to be able to write and read from a file
     */
    @Test
    public void testWriteAndReadLogin(){
        DatabaseParameterManager.writeLogin("max", "password", "www.world.com/");
        JSONObject userObject = DatabaseParameterManager.readLogin();
        String password = userObject.getString("password");
        String username = userObject.getString("username");
        String url = userObject.getString("url");
        assertEquals(password, "password");
        assertEquals(username, "max");
        assertEquals(url, "www.world.com/");
    }

    /**
     * Test to be able to overwrite the data inside the file
     */
    @Test
    public void OverWriteFile(){
        DatabaseParameterManager.writeLogin("max", "password", "www.world.com/");
        DatabaseParameterManager.writeLogin("admin", "adminpass", "differentURL");
        String password = userObject.getString("password");
        String username = userObject.getString("username");
        String url = userObject.getString("url");
        assertEquals(password, "adminpass");
        assertEquals(username, "admin");
        assertEquals(url, "differentURL");
    }



}
