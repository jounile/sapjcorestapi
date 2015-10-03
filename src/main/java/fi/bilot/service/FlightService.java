package fi.bilot.service;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.sap.conn.jco.ext.Environment;

import fi.bilot.Constants;
import fi.bilot.CustomDestinationDataProvider;
import fi.bilot.flight.FlightDetails;
import fi.bilot.pojo.Flight;

@Service
@Path("/flight")
public class FlightService {
	

    private static Flight flight = new Flight();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) //{"Flight":{"carrier":"LH","connectionNumber":"0400","date":"20151010"}} 
	@Produces(MediaType.APPLICATION_JSON)
	public String flightService(Flight fl) {
		
		flight.carrier = fl.carrier;
		flight.connectionNumber = fl.connectionNumber;
		flight.date = fl.date;
	
		FlightDetails flightDetails = new FlightDetails();
		String ret = flightDetails.getFlightJSON(flight).toString();
		
        return ret;       
	}
}
