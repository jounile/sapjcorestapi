package fi.bilot;

import java.io.FileInputStream;
import java.util.Properties;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

import fi.bilot.CustomDestinationDataProvider;

/**
 * SAP JCO REST API 
 * The purpose of this library is to provide connectivity to SAP functions via REST.
 * This library can be used as a boilerplate for web and mobile applications.
 *  
 * @author jounile
 *
 */
public class SapJcoRestApi {

    public static void main(String[] args) throws Exception {
    	// Add {sapjco-install-path} to the DYLD_LIBRARY_PATH environment variable.
    	System.setProperty("java.library.path", "/Users/jounile/sapjco3-darwinintel64-3.0.13");
        JCoDestination jcoDestination = getDestination();
        pingDestination(jcoDestination);
        requestSystemDetails(jcoDestination);

    }
    
	private static JCoDestination getDestination() throws JCoException {
  
		CustomDestinationDataProvider myProvider = CustomDestinationDataProvider.getInstance(); 
        Environment.registerDestinationDataProvider(myProvider);
        
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
        
        String destinationName = "DESTINATION_WITH_POOL";
        myProvider.addDestination(destinationName, connectProperties);     
        JCoDestination jcoDestination = JCoDestinationManager.getDestination(destinationName);
        
        return jcoDestination;
	}

	private static void pingDestination(JCoDestination jcoDestination) {
		try{  
        	System.out.println("Pinging SAP ERP...");
        	jcoDestination.ping();  
            System.out.println("Destination is ok");
        } catch(Exception e){  
            e.printStackTrace();  
            System.out.println("Destination is invalid");  
        }
	}
    
    public static Properties getConnectionProperties(){
		Properties prop = new Properties();
    	try {
    		FileInputStream fis = new FileInputStream("connection.properties");
    		prop.load(fis);
    	} catch(Exception e){
    		System.out.println(e.toString());
    	}
    	return prop;
    }
    
    public static void requestSystemDetails(JCoDestination destination) throws JCoException {

    	JCoFunction function = destination.getRepository().getFunction("STFC_CONNECTION");
    	if (function == null) {
    		throw new RuntimeException("Function STFC_CONNECTION not found in SAP.");
    	}

    	function.getImportParameterList().setValue("REQUTEXT", "Requesting SAP system details.");
    	try
    	{
    		function.execute(destination);
    	} catch (AbapException e) {
    		System.out.println(e.toString());
    	    throw new Error("FAIL: Error occured while executing function STFC_CONNECTION");
    	}

    	System.out.println("STFC_CONNECTION finished:");
        System.out.println("Echo: " + function.getExportParameterList().getString("ECHOTEXT"));
   	    System.out.println("Response: " + function.getExportParameterList().getString("RESPTEXT"));
   	    System.out.println();
   	  }
}