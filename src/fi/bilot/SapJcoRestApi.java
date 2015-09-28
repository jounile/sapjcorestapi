package fi.bilot;

import java.io.FileInputStream;
import java.util.Properties;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
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

	public static void main(String[] args) throws Exception 
    {
    	// Add {sapjco-install-path} to the DYLD_LIBRARY_PATH environment variable.
    	//System.setProperty("java.library.path", "/Users/jounile/sapjco3-darwinintel64-3.0.13");
        JCoDestination jcoDestination = getDestination();
        pingDestination(jcoDestination);
        requestSystemDetails(jcoDestination);
        callWithStructure(jcoDestination);
        callWithTable(jcoDestination);

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

	private static void pingDestination(JCoDestination jcoDestination) 
	{
		try{  
        	System.out.println("Pinging " + jcoDestination.getDestinationID() + " ...");
        	jcoDestination.ping();  
            System.out.println("Ping ok");
        } catch(Exception e){  
            e.printStackTrace();  
            System.out.println("Destination is invalid");  
        }
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
    
    public static void requestSystemDetails(JCoDestination destination) throws JCoException
    {
        JCoFunction function = destination.getRepository().getFunction("STFC_CONNECTION");
        if(function == null)
            throw new RuntimeException("Function STFC_CONNECTION not found in SAP.");

        function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");

        try
        {
        	System.out.println("Calling STFC_CONNECTION");
            function.execute(destination);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }
        System.out.println(" Echo: " + function.getExportParameterList().getString("ECHOTEXT"));
        System.out.println(" Response: " + function.getExportParameterList().getString("RESPTEXT"));
        System.out.println();
    }

    public static void callWithStructure(JCoDestination destination) throws JCoException
    {
        JCoFunction function = destination.getRepository().getFunction("RFC_SYSTEM_INFO");
        if(function == null)
            throw new RuntimeException("RFC_SYSTEM_INFO not found in SAP.");

        try
        {
        	System.out.println("Calling RFC_SYSTEM_INFO");
            function.execute(destination);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }

        JCoStructure exportStructure = function.getExportParameterList().getStructure("RFCSI_EXPORT");
        System.out.println("System info for " + destination.getAttributes().getSystemID() + ":\n");
        for(int i = 0; i < exportStructure.getMetaData().getFieldCount(); i++) 
        {
            System.out.println(exportStructure.getMetaData().getName(i) + ":\t" + exportStructure.getString(i));
        }
        System.out.println();

        System.out.println("The same using field iterator: \nSystem info for " + destination.getAttributes().getSystemID() + ":\n");
        for(JCoField field : exportStructure)
        {
            System.out.println(field.getName() + ":\t" + field.getString());
        }
        System.out.println();
    }

    public static void callWithTable(JCoDestination destination) throws JCoException
    {
        JCoFunction function = destination.getRepository().getFunction("BAPI_COMPANYCODE_GETLIST");
        if(function == null)
            throw new RuntimeException("BAPI_COMPANYCODE_GETLIST not found in SAP.");

        try
        {
        	System.out.println("Calling BAPI_COMPANYCODE_GETLIST");
            function.execute(destination);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }

        JCoStructure returnStructure = function.getExportParameterList().getStructure("RETURN");
        if (! (returnStructure.getString("TYPE").equals("")||returnStructure.getString("TYPE").equals("S"))  )   
        {
           throw new RuntimeException(returnStructure.getString("MESSAGE"));
        }

        JCoTable codes = function.getTableParameterList().getTable("COMPANYCODE_LIST");
        for (int i = 0; i < codes.getNumRows(); i++) 
        {
            codes.setRow(i);
            System.out.println(codes.getString("COMP_CODE") + '\t' + codes.getString("COMP_NAME"));
        }

        codes.firstRow();
        for (int i = 0; i < codes.getNumRows(); i++, codes.nextRow()) 
        {
            function = destination.getRepository().getFunction("BAPI_COMPANYCODE_GETDETAIL");
            if (function == null) 
                throw new RuntimeException("BAPI_COMPANYCODE_GETDETAIL not found in SAP.");

            function.getImportParameterList().setValue("COMPANYCODEID", codes.getString("COMP_CODE"));
            function.getExportParameterList().setActive("COMPANYCODE_ADDRESS",false);

            try
            {
                function.execute(destination);
            }
            catch (AbapException e)
            {
                System.out.println(e.toString());
                return;
            }

            returnStructure = function.getExportParameterList().getStructure("RETURN");
            if (! (returnStructure.getString("TYPE").equals("") ||
                   returnStructure.getString("TYPE").equals("S") ||
                   returnStructure.getString("TYPE").equals("W")) ) 
            {
                throw new RuntimeException(returnStructure.getString("MESSAGE"));
            }

            JCoStructure detail = function.getExportParameterList().getStructure("COMPANYCODE_DETAIL");

            System.out.println(detail.getString("COMP_CODE") + '\t' +
                               detail.getString("COUNTRY") + '\t' +
                               detail.getString("CITY"));
        }
    }
    
}