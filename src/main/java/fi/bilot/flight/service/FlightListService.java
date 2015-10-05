package fi.bilot.flight.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import fi.bilot.flight.FlightAPI;
import fi.bilot.flight.flightlist.FlightListSearchParameters;

@Service
@Path("/flightlist")
public class FlightListService {
	
    private static FlightListSearchParameters flsp = new FlightListSearchParameters();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getFlightList(FlightListSearchParameters params) {
		
		flsp.fromCountry = params.fromCountry;
		flsp.fromCity = params.fromCity;
		flsp.toCountry = params.toCountry;
		flsp.toCity = params.toCity;
		flsp.maxRead = params.maxRead;
	
		FlightAPI flightApi = new FlightAPI();
		return flightApi.getFlightListJSON(flsp).toString();      
	}

}
