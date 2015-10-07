package fi.bilot.flightlist;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.bilot.customer.CustomerAPI;

@Service
@Path("/flightlist")
public class FlightListService {
	
	@Autowired
	protected FlightListAPI flightListApi;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getFlightList(FlightListSearchParameters params) {
		
		FlightListSearchParameters flsp = new FlightListSearchParameters();
		flsp.fromCountry = params.fromCountry;
		flsp.fromCity = params.fromCity;
		flsp.toCountry = params.toCountry;
		flsp.toCity = params.toCity;
		flsp.maxRead = params.maxRead;

		return flightListApi.getFlightListJSON(flsp).toString();      
	}

}
