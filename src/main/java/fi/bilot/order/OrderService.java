package fi.bilot.order;

import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

import com.sap.conn.jco.ext.Environment;

import fi.bilot.Constants;
import fi.bilot.CustomDestinationDataProvider;

@Service
@Path("/order")
public class OrderService {
	
	@GET
	@Path("/{number}")
	@Produces("application/json")
	public String getOrder(@PathParam("number") String number) {
		OrderDetails ord = new OrderDetails();

		return ord.getOrderJSON(number).toString();
	}
}
