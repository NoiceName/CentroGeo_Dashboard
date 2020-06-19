package resources;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.http.Cookie;
import javax.ws.rs.core.*;
import javax.xml.parsers.ParserConfigurationException;

import com.google.common.net.HttpHeaders;
import cookie_manager.Secured;
import org.apache.commons.io.IOUtils;
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
	 * @return - A JSON Object which has a single tag of 'result' which is set to true in the case the login succeeds and false otherwise. {result:"boolean"}
	 */
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(String userInformation) {
		JSONObject userJson = new JSONObject(userInformation);
		String username = userJson.getString("username");
		String password = userJson.getString("password");

		JSONObject response = new JSONObject();
		
		//verify hash_password
		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
		String returnPasswordHash = UserDAO.instance.getUserPassword(username); 
		boolean match = argon2.verify(returnPasswordHash, password);
		
			
		if (returnPasswordHash == null||!match){
			response.put("result", "false");
			return Response.serverError().entity(response.toString()).build();
		} else {
			//Generate and save a new token and send it to the user inform of a cookie 
			String token = CookieManager.assignCookie(new User(username,returnPasswordHash)); 
			NewCookie authCookie = new NewCookie(HttpHeaders.AUTHORIZATION, token, "/",
					null, null, 60*60*24, false, true);
			response.put("result", "true");
			return Response.ok().entity(response.toString()).cookie(authCookie).build();
		}
		
	}

	@POST
	@Secured
	@Path("/change_password")
	@Consumes("application/json")
	@Produces("application/json")
	public Response changePassword(String userInformation) {
		JSONObject userJson = new JSONObject(userInformation);
		String username = userJson.getString("username");
		String oldPassword = userJson.getString("oldPassword");
		String newPassword = userJson.getString("newPassword");

		JSONObject response = new JSONObject();

		System.out.println(username + oldPassword + newPassword);

		//verify hash_password
		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);
		String returnPasswordHash = UserDAO.instance.getUserPassword(username);
		boolean match = argon2.verify(returnPasswordHash, oldPassword);

		if (match){
			String passwordHash = argon2.hash(4, 1024 * 1024, 8, newPassword);
			UserDAO.instance.setUserPassword(username, passwordHash);
			response.put("result", "true");
			return Response.ok().entity(response.toString()).build();
		} else {
			response.put("result", "false");
			return Response.serverError().entity(response.toString()).build();
		}
	}
	
}
