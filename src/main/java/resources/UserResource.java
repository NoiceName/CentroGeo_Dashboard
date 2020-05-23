package resources;



import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import dao.UserDAO;
import model.User;

@Path("/UserResource")
public class UserResource {
	
	private static final String SUCCESS_RESULT="<result>success</result>";
//	private static final String FAILURE_RESULT="<result>failure</result>";
	private static final String FAILURE_RESULT_PASSWORD="<result>failurePassword</result>";	
	private static final String FAILURE_RESULT_NAME="<result>failureName</result>";	
	

	
	@POST
	@Path("/users")
	@Produces({ MediaType.APPLICATION_ATOM_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getUser(@FormParam("username") String name, @FormParam("password") String password,
			@Context HttpServletResponse servletResponse) {

		User user = UserDAO.instance.getModel().get(name);

		if (user == null) {
		
		    return FAILURE_RESULT_NAME;	//user name doesn't exist
	    }
		else if(!password.equals(user.getPassword()) ){
	
			return FAILURE_RESULT_PASSWORD;	//password is wrong
		}
		else {

		return SUCCESS_RESULT;	
		}

	}

}
