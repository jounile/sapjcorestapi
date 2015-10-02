package fi.bilot.service;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

import com.sap.conn.jco.ext.Environment;

import fi.bilot.Constants;
import fi.bilot.CustomDestinationDataProvider;
import fi.bilot.flight.FlightDetails;

@Service
@Path("/flight")
public class FlightService {
	
	@GET
	//@Path("/{number}")
	@Produces("application/json")
	public String isPrime() {
		FlightDetails ord = new FlightDetails();
		
		//System.out.println(ord.getHeadersOutXML(number));		
		//System.out.println(ord.getStatusHeadersOutXML(number));		
		//System.out.println(ord.getStatusItemsOutXML(number));
		
		//return ord.getHeadersOutJSON(number).toString();
		//return ord.getStatusHeadersOutJSON(number).toString();
		//return ord.getStatusItemsOutJSON(number).toString();
		return ord.getFlightJSON().toString();
	}
}
