package resources;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.http.Cookie;

import javax.ws.rs.core.*;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import cookie_manager.CookieManager;
import dao.UserDAO;
import model.Database;
import model.User;
import org.junit.platform.engine.support.descriptor.FileSystemSource;
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
		
		User user = UserDAO.instance.getModel().get(username);
		JSONObject response = new JSONObject();
		if (user == null||!password.equals(user.getPassword())) {
			response.put("result", "false");
		}
		else {
			//Generate and save a new token and send it to the user inform of a cookie 
			String token = CookieManager.assignCookie(user);
			Cookie authCookie = new Cookie("auth",token);
			authCookie.setMaxAge(60*60);
			httpResponse.addCookie(authCookie);
			response.put("result","true");
			
		}
		return response.toString();
	}
	

	@Path("/zip")
	@POST
	@Consumes("application/zip")
	public void getZip(InputStream stream){
		Database.loadPGSQL();
		Database newDb = new Database();

		try {
			newDb.connectPGSQL();
			Connection connection = newDb.getConnection();
			System.out.println("Downloaded file");
			extraction.ZipExtraction.getZipData(stream, connection);
			System.out.println("File added to database");
		} catch (IOException e) {
			e.printStackTrace(); }
	}

}
