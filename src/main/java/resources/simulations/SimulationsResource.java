package resources.simulations;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("/simulation")
public class SimulationsResource {
	
	@GET
	@Produces("text/html")
	public void getSimulationsPage(@Context HttpServletResponse response) {
		forwardUser("../viewing-simulations/display.html", response);
	}
	
	public void forwardUser(String path, HttpServletResponse response) {
		try {
			response.sendRedirect(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
