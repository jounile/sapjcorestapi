package fi.bilot.customersearch;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/customersearch")
public class CustomerSearchService {

	@Autowired
	protected CustomerSearchAPI customerSearchApi;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomerList(CustomerSearchParameters params) {
		
		CustomerSearchParameters csp = new CustomerSearchParameters();
		csp.customerNro = params.customerNro;
		csp.salesOrg = params.salesOrg;
		csp.distributionChannel = params.distributionChannel;
		csp.division = params.division;

		return customerSearchApi.getCustomersSearchResultsJSON(csp).toString();      
	}

}