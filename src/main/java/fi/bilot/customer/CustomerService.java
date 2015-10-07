package fi.bilot.customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.bilot.flight.FlightSearchParameters;

@Service
@Path("/customer")
public class CustomerService {

	@Autowired
	protected CustomerAPI customerApi;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getFlightList(CustomerSearchParameters params) {
		
		CustomerSearchParameters csp = new CustomerSearchParameters();
		csp.customerNro = params.customerNro;
		csp.salesOrg = params.salesOrg;
		csp.distributionChannel = params.distributionChannel;
		csp.division = params.division;

		return customerApi.getCustomerJSON(csp).toString();      
	}

}
