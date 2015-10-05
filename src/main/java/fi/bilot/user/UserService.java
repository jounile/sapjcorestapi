package fi.bilot.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

@Service
@Path("/user")
public class UserService {
	
    private static User usr = new User();

	@GET
	@Path("/{username}")
	public String getFlightList(@PathParam("username") String username) {
		UserDetailsAPI userDetailsApi = new UserDetailsAPI();
		return userDetailsApi.getUserJSON(username).toString();      
	}

}
