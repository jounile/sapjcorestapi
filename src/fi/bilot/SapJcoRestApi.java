package fi.bilot;

import fi.bilot.flight.FlightDetails;
import fi.bilot.flight.FlightList;
import fi.bilot.order.OrderJcoFunctionCalls;
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
	private static FlightList fl = new FlightList();
	private static FlightDetails fd = new FlightDetails();
	private static OrderJcoFunctionCalls order = new OrderJcoFunctionCalls();
	
	public static void main(String[] args) throws Exception 
    {
     
        //test.pingDestination();
        //test.requestSystemDetails();
        //test.callWithStructure();
        //test.callWithTable();

        //order.retrieveSalesDocumentFromErp("1");
        //fl.getFlightList();
        fd.getFlightDetails();

    }    
}