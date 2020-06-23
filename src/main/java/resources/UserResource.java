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
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import cookie_manager.CookieManager;
import dao.UserDAO;
import model.User;


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
		String rawPassword = userJson.getString("password");

		JSONObject response = new JSONObject();
	    String returnHashedPassword = UserDAO.instance.getUserPassword(username);
		
		// verify password using SCryptPasswordEncoder
		int cpuCost = (int) Math.pow(2, 14); // factor to increase CPU costs
		int memoryCost = 8;      // increases memory usage
		int parallelization = 1; // parallelization
		int keyLength = 32;      // key length in bytes
		int saltLength = 64;     // salt length in bytes

		SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(
		  cpuCost, 
		  memoryCost,
		  parallelization,
		  keyLength,
		  saltLength);
		boolean match = sCryptPasswordEncoder.matches(rawPassword, returnHashedPassword);
			
		if (returnHashedPassword == null||!match){
			response.put("result", "false");
			return Response.serverError().entity(response.toString()).build();
			}
		else {
			//Generate and save a new token and send it to the user inform of a cookie 
			String token = CookieManager.assignCookie(new User(username,returnHashedPassword)); 
			NewCookie authCookie = new NewCookie(HttpHeaders.AUTHORIZATION, token, "/",
					null, null, 60*60*24, false, true);
			response.put("result", "true");
			return Response.ok().entity(response.toString()).cookie(authCookie).build();
			
		    }
		
	}
	
}
