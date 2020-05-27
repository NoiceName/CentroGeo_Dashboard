package resources;



import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.http.Cookie;
import javax.ws.rs.core.*;

import org.json.JSONObject;

import cookie_manager.CookieManager;
import dao.UserDAO;
import model.User;

@Path("/UserResource")
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
	 * @param servletResponse
	 * Redirects the user to the login page
	 */
	@GET
	@Produces("text/html")
	public void loginPage(@Context HttpServletResponse servletResponse) {
		try {
			System.out.println("Return something");
			servletResponse.sendRedirect("../login/log_in.html");		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//This codes receives json from a post request and creates a user object and returns it back to the client
//	@POST
//	@POST
//	@Produces("application/json")
//	@Consumes("application/json")
//	public User login(String json) {
//		JSONObject input = new JSONObject(json);
//		User newUser = new User(input.getString("username"),input.getString("password"));
//		return newUser;
//	}

	@POST
	@Consumes("application/json")
	public void login(String userInformation) {
		JSONObject userJson = new JSONObject(userInformation);
		System.out.println(userJson);
	}

	/**
	 * Processes the user input login information.
	 * In the case the login is successful a token is generated and is set as a cookie.
	 * @param userInformation
	 * @param httpResponse
	 * @return - A JSON Object which has a single tag of 'result' which is set to true in the case the login succeeds and false otherwise.
	 */
	@POST
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
		String returnPassword = UserDAO.instance.getUserPassword(username);
		JSONObject response = new JSONObject();
		if (returnPassword == null||!returnPassword.equals(password)){
			response.put("result", "false");
		}
		else {
			//Generate and save a new token and send it to the user inform of a cookie 
			String token = CookieManager.assignCookie(new User(username,returnPassword)); 
			Cookie authCookie = new Cookie("auth",token);
			authCookie.setMaxAge(60*60);
			httpResponse.addCookie(authCookie);
			response.put("result","true");
		}
		return response.toString();
	}
	
}
