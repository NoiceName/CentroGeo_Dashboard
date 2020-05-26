package resources;



import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import org.json.JSONObject;

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
	
	
	/**
	 * Receives a User object from the login page
	 * Use this for reference if trying to send some json back to the client!!!
	 * @param newUser
	 */
//	@POST
//	@Produces("application/json")
//	@Consumes("application/json")
//	public User login(String json) {
//		JSONObject input = new JSONObject(json);
//		User newUser = new User(input.getString("username"),input.getString("password"));
//		return newUser;
//	}



	
	@POST
	@Path("/users")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void getUser(@FormParam("username") String username, 
			@FormParam("password") String password,@Context HttpServletResponse servletResponse
			) {

		User user = UserDAO.instance.getModel().get(username);

		if (user == null||!password.equals(user.getPassword())) {
			
			try {
				
				servletResponse.sendRedirect("../../login/log_in_alert.html");		
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				servletResponse.sendRedirect("../../homepage.html");		
				
			} catch (IOException e) {
				e.printStackTrace();
			}		  
		}
	}
}
