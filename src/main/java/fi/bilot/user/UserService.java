package fi.bilot.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/user")
public class UserService {
	
	@Autowired
	protected UserDetailsAPI userDetailsApi;
	
	@GET
	@Path("/{username}")
	public String getFlightList(@PathParam("username") String username) {

		return userDetailsApi.getUserJSON(username).toString();      
	}

}
