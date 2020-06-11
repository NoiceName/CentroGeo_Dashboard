package cookie_manager;

import com.google.common.net.HttpHeaders;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static cookie_manager.CookieManager.checkCookie;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Authentication");
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (!(authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " "))) {
			if (authorizationHeader != null){
				System.out.println(authorizationHeader.toLowerCase());
			}
			abort(requestContext);
			System.out.println("Invalid token");
			return;
		}

		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
		System.out.println("Token: " + token);

		try {
			// Validate the token
			if (!checkCookie(token)){
				throw new Exception();
			}

		} catch (Exception e) {
			abort(requestContext);
			System.out.println("Invalid token");
			return;
		}

		System.out.println("Correct Token");

	}

	public void abort(ContainerRequestContext requestContext){
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
	}
}
