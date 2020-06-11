package resources;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.http.Cookie;

import javax.ws.rs.core.*;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import cookie_manager.CookieManager;
import dao.UserDAO;
import model.Database;
import model.User;
import org.junit.platform.engine.support.descriptor.FileSystemSource;
import org.xml.sax.SAXException;

@Path("/user_resource")
public class UserResource {
	
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String FAILURE_RESULT_PASSWORD="<result>failurePassword</result>";	
	private static final String FAILURE_RESULT_NAME="<result>failureName</result>";	
	

	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;

	@Context
	Response response;
	
	/**
	 * Processes the user input login information.
	 * In the case the login is successful a token is generated and is set as a cookie.
	 * @param userInformation - JSON{username: "String", password:"String"} 
	 * @param httpResponse  
	 * @return - A JSON Object which has a single tag of 'result' which is set to true in the case the login succeeds and false otherwise. {result:"boolean"}
	 */
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public String login(String userInformation, @Context HttpServletResponse httpResponse) {
		JSONObject userJson = new JSONObject(userInformation);
		String username = userJson.getString("username");
		String password = userJson.getString("password");
	
		/* I have created the method to check if the username is in the database, 
		 * and if the returned password matches the given password		
		 * */
		
 //		User user = UserDAO.instance.getModel().get(username);
		String returnPassword = UserDAO.instance.getModel().get(username).getPassword();
		JSONObject response = new JSONObject();
		if (returnPassword == null||!returnPassword.equals(password)){
			response.put("result", "false");
		}
		else {
			//Generate and save a new token and send it to the user inform of a cookie 
			String token = CookieManager.assignCookie(new User(username,returnPassword)); 
			Cookie authCookie = new Cookie("auth",token);
			authCookie.setMaxAge(60*60*24);
			httpResponse.addCookie(authCookie);
			response.put("result","true");
			response.put("token", token);
			
		}
		return response.toString();
	}
	
}
