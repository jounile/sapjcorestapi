package fi.bilot.test;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;

public class TestJcoFunctionCalls {
	
	//private static JcoConnection con = new JcoConnection();
	
	public void pingDestination() throws JCoException
	{
		try{  
			JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
        	System.out.println("Pinging " + jcoDestination.getDestinationID() + " ...");
        	jcoDestination.ping();  
            System.out.println("Ping ok");
        } catch(Exception e){  
            e.printStackTrace();  
            System.out.println("Destination is invalid");  
        }
	}
    
    public void requestSystemDetails() throws JCoException
    {
    	JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
        JCoFunction function = jcoDestination.getRepository().getFunction("STFC_CONNECTION");
        if(function == null)
            throw new RuntimeException("Function STFC_CONNECTION not found in SAP.");

        function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");

        try
        {
        	System.out.println("Calling STFC_CONNECTION");
            function.execute(jcoDestination);
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

    public void callWithStructure() throws JCoException
    {
    	JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
        JCoFunction function = jcoDestination.getRepository().getFunction("RFC_SYSTEM_INFO");
        if(function == null)
            throw new RuntimeException("RFC_SYSTEM_INFO not found in SAP.");

        try
        {
        	System.out.println("Calling RFC_SYSTEM_INFO");
            function.execute(jcoDestination);
        }
        catch(AbapException e)
        {
            System.out.println(e.toString());
            return;
        }

        JCoStructure exportStructure = function.getExportParameterList().getStructure("RFCSI_EXPORT");
        System.out.println("System info for " + jcoDestination.getAttributes().getSystemID() + ":\n");
        for(int i = 0; i < exportStructure.getMetaData().getFieldCount(); i++) 
        {
            System.out.println(exportStructure.getMetaData().getName(i) + ":\t" + exportStructure.getString(i));
        }
        System.out.println();

        System.out.println("The same using field iterator: \nSystem info for " + jcoDestination.getAttributes().getSystemID() + ":\n");
        for(JCoField field : exportStructure)
        {
            System.out.println(field.getName() + ":\t" + field.getString());
        }
        System.out.println();
    }

    public void callWithTable() throws JCoException
    {
    	JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
        JCoFunction function = jcoDestination.getRepository().getFunction("BAPI_COMPANYCODE_GETLIST");
        if(function == null)
            throw new RuntimeException("BAPI_COMPANYCODE_GETLIST not found in SAP.");

        try
        {
        	System.out.println("Calling BAPI_COMPANYCODE_GETLIST");
            function.execute(jcoDestination);
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
            function = jcoDestination.getRepository().getFunction("BAPI_COMPANYCODE_GETDETAIL");
            if (function == null) 
                throw new RuntimeException("BAPI_COMPANYCODE_GETDETAIL not found in SAP.");

            function.getImportParameterList().setValue("COMPANYCODEID", codes.getString("COMP_CODE"));
            function.getExportParameterList().setActive("COMPANYCODE_ADDRESS",false);

            try
            {
                function.execute(jcoDestination);
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
