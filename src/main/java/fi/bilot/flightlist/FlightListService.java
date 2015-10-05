package fi.bilot.flightlist;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

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
	
		FlightListAPI flightListApi = new FlightListAPI();
		return flightListApi.getFlightListJSON(flsp).toString();      
	}

}
