package resources;



import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import dao.UserDAO;
import model.User;
import org.xml.sax.SAXException;

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
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void login(User newUser) {
		System.out.println("hello");
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
	
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void uploadZip(@FormParam("zip-file") String file, @Context HttpServletResponse servletResponse) {
		try {
			servletResponse.sendRedirect("../../homepage.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Path("/zip")
	@POST
	@Consumes("application/zip")
	public void getZip(InputStream stream){
		System.out.println("File received");
		// try {
		// 	Class.forName("org.postgresql.Driver");
		// }
		// catch (ClassNotFoundException cnfe) {
		// 	System.err.println("Error loading driver: " + cnfe);
		// }
		//
		// String url = "jdbc:postgresql://localhost:5433/centrogeo";
		// String username = "postgres";
		// String password = "1YIrISqSsLxYFI8Itig6";
		//
		// try {
		// 	Connection connection = DriverManager.getConnection(url, username, password);
		// 	System.out.println("Downloaded file");
		// 	extraction.ZipExtraction.getZipData(stream, connection);
		// } catch (SQLException | IOException e) {
		// 	e.printStackTrace();
		// }
	}

	
}
