package fi.bilot.customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

@Service
@Path("/customer")
public class CustomerService {
	
    private static Customer cust = new Customer();

	@GET
	@Path("/{customerNro}")
	public String getFlightList(@PathParam("customerNro") String customerNro) {
		CustomerAPI customerApi = new CustomerAPI();
		return customerApi.getCustomerJSON(customerNro).toString();      
	}

}
