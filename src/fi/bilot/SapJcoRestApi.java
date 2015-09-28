package fi.bilot;

import java.io.FileInputStream;
import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

import fi.bilot.CustomDestinationDataProvider;
import fi.bilot.flight.FlightJcoFunctionCalls;
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
	private static FlightJcoFunctionCalls example = new FlightJcoFunctionCalls();
	private static OrderJcoFunctionCalls order = new OrderJcoFunctionCalls();
	
	public static void main(String[] args) throws Exception 
    {
        JCoDestination jcoDestination = getDestination();
        
        //test.pingDestination(jcoDestination);
        //test.requestSystemDetails(jcoDestination);
        //test.callWithStructure(jcoDestination);
        //test.callWithTable(jcoDestination);


        //order.retrieveSalesDocumentFromErp(jcoDestination, "1");
        //example.getFlightList(jcoDestination);
        example.getFlightDetails(jcoDestination);

    }
    
	private static JCoDestination getDestination() throws JCoException 
	{
		CustomDestinationDataProvider provider = CustomDestinationDataProvider.getInstance(); 
		Environment.registerDestinationDataProvider(provider);
        
        Properties conProp = getConnectionProperties();
        Properties connectProperties = new Properties(); 
        connectProperties.setProperty(DestinationDataProvider.JCO_DEST, conProp.getProperty("DESTINATION_ID")); 
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, conProp.getProperty("ASHOST"));     
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, conProp.getProperty("SYSNR"));     
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, conProp.getProperty("CLIENT"));     
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, conProp.getProperty("USER"));     
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, conProp.getProperty("PASSWD"));   
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, conProp.getProperty("LANG"));      
        
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, conProp.getProperty("CONNECTION_POOL_SIZE"));
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, conProp.getProperty("CONNECTION_POOL_PEAK_LIMIT"));
        
        String destinationName = "ABAP_AS_POOLED";
        provider.addDestination(destinationName, connectProperties);     
        JCoDestination jcoDestination = JCoDestinationManager.getDestination(destinationName);
        
        return jcoDestination;
	}

	public static Properties getConnectionProperties()
    {
		Properties prop = new Properties();
    	try {
    		FileInputStream fis = new FileInputStream("connection.properties");
    		prop.load(fis);
    	} catch(Exception e){
    		System.out.println(e.toString());
    	}
    	return prop;
    }
    
}