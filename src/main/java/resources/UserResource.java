package resources;

import java.io.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import dao.UserDAO;
import model.User;

@Path("UserResource")
public class UserResource {
	
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String FAILURE_RESULT="<result>failure</result>";
	private static final String FAILURE_RESULT_PASSWORD="<result>failurePassword</result>";	
	private static final String FAILURE_RESULT_NAME="<result>failureName</result>";	
	
	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createUser(@PathParam("name") String name,
	    @PathParam("password") String password,
	    @Context HttpServletResponse servletResponse) throws IOException{
	      User user = new User(name, password);
	      int result = UserDAO.instance.addUser(user);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }
	
	@GET
	@Path("/users/{name}/{password}")
	@Produces({ MediaType.APPLICATION_ATOM_XML})
	public String getUser(@PathParam("name") String name, @PathParam("password") String password) {

		User user = UserDAO.instance.getUser(name);

		if (user == null) {
		
		    return FAILURE_RESULT_NAME;	//user name doesn't exist
	    }
		else if(user.getPassword()!=password) {
	
			return FAILURE_RESULT_PASSWORD;	//password is wrong
		}
		return SUCCESS_RESULT;
		
	}

}
