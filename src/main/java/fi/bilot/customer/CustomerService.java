package fi.bilot.customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import fi.bilot.flight.FlightSearchParameters;

@Service
@Path("/customer")
public class CustomerService {
	
	private static CustomerSearchParameters csp = new CustomerSearchParameters();
    
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getFlightList(CustomerSearchParameters params) {
		
		csp.customerNro = params.customerNro;
		csp.salesOrg = params.salesOrg;
		csp.distributionChannel = params.distributionChannel;
		csp.division = params.division;
		
		CustomerAPI customerApi = new CustomerAPI();
		return customerApi.getCustomerJSON(csp).toString();      
	}

}
