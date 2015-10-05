package fi.bilot.flight;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import fi.bilot.flightlist.FlightListSearchParameters;

@Service
@Path("/flight")
public class FlightService {
	
    private static FlightSearchParameters fsp = new FlightSearchParameters();
    
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getFlight(FlightSearchParameters params) {
		
		fsp.carrier = params.carrier;
		fsp.connectionNumber = params.connectionNumber;
		fsp.date = params.date;
	
		FlightAPI flightApi = new FlightAPI();
		return flightApi.getFlightJSON(fsp).toString();       
	}
	
}
