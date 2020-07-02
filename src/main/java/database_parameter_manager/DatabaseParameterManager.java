package database_parameter_manager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Used to manipulate the database parameter file.
 */
public class DatabaseParameterManager {

    /**
     * Writes login details to a json file and saves them if the file cannot be found
     * it will be created.
     */
    public static void writeParameters(String username , String password, String url){
        if(!checkIfExists()){
            createJSONParameterFile();
        }
        //In the case the file has not been created or the file is not empty
        if(!(readParameters().equals(new JSONArray()))){
            clearParameterFile();
        }
        //Read in the parameters into a JSONObject then into an array
        JSONObject parameters = new JSONObject();
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("url", url);
        JSONArray arr = new JSONArray();
        arr.add(parameters);
        try {
            FileWriter jsonFile = new FileWriter("db_log.json");
            jsonFile.write(arr.toString());
            jsonFile.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Could not write database parameters information to a file");
        }
    }

    /**
     * Creates a new db_log.json file
     */
    private static void createJSONParameterFile() {
        File newJSONFile = new File("db_log.json");
        try {
            newJSONFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Unable to create a new file!");
        }
    }

    /**
     * Checks if the db_log.json file exists
     * @return true if exists false otherwise
     */
    public static boolean checkIfExists(){
        File temp;
        boolean ex = false;
        temp = new File("db_log.json");
        if(temp.exists()){
            ex = true;
        }
        return ex;
    }

    /**
     * Deletes the parameter file and creates it again
     * @requires A file called db_log.json exists
     */
    private static void clearParameterFile(){
        File myFile = new File("db_log.json");
        myFile.delete();
        createJSONParameterFile();
    }

    /**
     * Read database parameters return an array of JSONObjects (expected to be 1 object with admin), creates a new file in the case it is empty.
     */
    public static JSONArray readParameters(){
        //Signals that the file is empty
        if(!checkIfExists()){
            createJSONParameterFile();
        }
        JSONParser jsonParser = new JSONParser();
        JSONArray arr = null;
        FileReader reader = null;
        try {
            reader = new FileReader("db_log.json");
            Object obj = jsonParser.parse(reader);
            arr = (JSONArray) obj;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Read parameter file does not exist");
        } catch (ParseException e) {
            //Return an empty array
            System.out.println("JSON file is empty");
            return new JSONArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * @return - Absolute path of the file
     * @requires - The file exists
     */
    public static String getFilePath(){
        File jsonFile = new File("db_log.json");
        return jsonFile.getAbsolutePath();
    }




    /* Todo add another variable to the database parameter file that will indicate whether the database can be initialised */
}
