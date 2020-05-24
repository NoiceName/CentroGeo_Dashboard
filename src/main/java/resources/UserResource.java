package resources;



import java.io.IOException;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

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
			servletResponse.sendRedirect("../login/log_in.html");		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
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
