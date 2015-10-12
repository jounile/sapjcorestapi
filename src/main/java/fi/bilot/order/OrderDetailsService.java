package fi.bilot.order;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/order")
public class OrderDetailsService {

	@Autowired
	protected OrderDetailsAPI orderDetailsApi;
	
	@GET
	@Path("/{number}")
	@Produces("application/json")
	public String getOrder(@PathParam("number") String number) {

		return orderDetailsApi.getOrderJSON(number).toString();
	}
}
