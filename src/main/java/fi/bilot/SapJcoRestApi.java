package fi.bilot;

import fi.bilot.flight.FlightCustomerList;
import fi.bilot.flight.FlightDetails;
import fi.bilot.flight.FlightList;
import fi.bilot.order.OrderDetails;
import fi.bilot.test.Monitoring;
import fi.bilot.test.TestJcoFunctionCalls;

/**
 * SAP JCO REST API 
 * The purpose of this library is to provide connectivity to SAP functions via REST.
 * This library can be used as a boilerplate for web and mobile applications.
 * 
 * Note: OSX
 * Add {sapjco-install-path} to the DYLD_LIBRARY_PATH environment variable.
 * System.setProperty("java.library.path", "/Users/jounile/sapjco3-darwinintel64-3.0.13");
 *  
 * @author jounile
 *
 */
public class SapJcoRestApi {

	private static TestJcoFunctionCalls test = new TestJcoFunctionCalls();
	private static Monitoring monitoring = new Monitoring();
	
	private static FlightList fl = new FlightList();
	private static FlightDetails fd = new FlightDetails();
	private static FlightCustomerList fcl = new FlightCustomerList();
	private static OrderDetails order = new OrderDetails();
	
	public static void main(String[] args) throws Exception 
    {
     
        //test.pingDestination();
        //test.requestSystemDetails();
        //test.callWithStructure();
        //test.callWithTable();
		//monitoring.getMonitoringData();
		
        //fl.getFlightList();
        //fd.getFlightDetails();
		//fcl.getFlightCustomerList();
		
		//order.retrieveSalesDocumentFromErp("1");

    }    
}