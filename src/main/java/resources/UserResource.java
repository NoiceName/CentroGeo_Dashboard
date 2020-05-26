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

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String getUser(String userInformation) {
		JSONObject userJson = new JSONObject(userInformation);
		String username = userJson.getString("username");
		String password = userJson.getString("password");
		
		User user = UserDAO.instance.getModel().get(username);
		JSONObject response = new JSONObject();
		if (user == null||!password.equals(user.getPassword())) {
			response.put("sucess", "success");
		}
		else {
			response.put("fail","fail");
		}
		System.out.println(response);
		return response.toString();
	}
}
