package resources;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.http.Cookie;
import javax.ws.rs.core.*;
import org.json.JSONObject;
import cookie_manager.CookieManager;
import dao.UserDAO;
import model.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;


@Path("/user_resource")
public class UserResource {
		

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
		
		//verify hash_password
		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
		String returnPasswordHash = UserDAO.instance.getUserPassword(username); 
		boolean match = argon2.verify(returnPasswordHash, password);
		
	
		/* I have created the method to check if the username is in the database, 
		 * and if the returned password matches the given password		
		 * */

		JSONObject response = new JSONObject();
			
			if (returnPasswordHash == null||!match){
			response.put("result", "false");
			}
		else {
			//Generate and save a new token and send it to the user inform of a cookie 
			String token = CookieManager.assignCookie(new User(username,returnPasswordHash)); 
			Cookie authCookie = new Cookie("auth",token);
			authCookie.setMaxAge(60*60*24);
			httpResponse.addCookie(authCookie);
			response.put("result","true");
			response.put("token", token);
			
		    }
		
		return response.toString();
	}
	
}
