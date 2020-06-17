package redirect;

import cookie_manager.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/")
public class Redirect {

	@Context
	private UriInfo uriInfo;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response redirectEmpty() {
		URI uri = uriInfo.getBaseUriBuilder().path("homepage.html").build();
		return Response.temporaryRedirect(uri).build();
	}

}
