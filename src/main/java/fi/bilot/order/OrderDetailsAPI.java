package fi.bilot.order;

import java.io.File;
import java.util.Properties;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.Environment;

import fi.bilot.Constants;
import fi.bilot.CustomDestinationDataProvider;
import fi.bilot.HelperFunctions;
import fi.bilot.JcoConnection;
import fi.bilot.order.SalesDocument;


public class OrderDetailsAPI
{

	public SalesDocument getOrder(String salesOrderNumber) 
	{
		SalesDocument salesDocument = new SalesDocument();
		
		try {
			JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			JCoFunction function = jcoDestination.getRepository().getFunction("BAPI_ISAORDER_GETDETAILEDLIST");

			JCoStructure importStructure = function.getImportParameterList().getStructure("I_BAPI_VIEW");		
			importStructure.setValue("HEADER", "X");
			importStructure.setValue("STATUS_H", "X");
			importStructure.setValue("STATUS_I", "X");
			
			JCoTable importTable = function.getTableParameterList().getTable("SALES_DOCUMENTS");
			
			importTable.appendRow();
			
			salesOrderNumber = HelperFunctions.HandleLeadingZeros(salesOrderNumber);
			importTable.setValue("VBELN", salesOrderNumber);
	      
			//System.out.println("Calling BAPI_ISAORDER_GETDETAILEDLIST");
			function.execute(jcoDestination);
			
			salesDocument.setSalesDocumentNumber(salesOrderNumber);
			
			if (importStructure.getValue("HEADER").toString().equalsIgnoreCase("X")) {
				JCoTable exportTable = function.getTableParameterList().getTable("ORDER_HEADERS_OUT");
				salesDocument.setOrderHeadersOut(exportTable);
				//System.out.println(exportTable);
			}
			
			if (importStructure.getValue("STATUS_H").toString().equalsIgnoreCase("X")) {
				JCoTable exportTable = function.getTableParameterList().getTable("ORDER_STATUSHEADERS_OUT");
				salesDocument.setOrderStatusheadersOut(exportTable);
				//System.out.println(exportTable);
			}
			
			if (importStructure.getValue("STATUS_I").toString().equalsIgnoreCase("X")) {
				JCoTable exportTable = function.getTableParameterList().getTable("ORDER_STATUSITEMS_OUT");
				salesDocument.setOrderStatusitemsOut(exportTable);
				//System.out.println(exportTable);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return salesDocument;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getHeadersOutJSON(String salesOrderNumber){
		SalesDocument sd = getOrder(salesOrderNumber);
		JCoTable table = sd.getOrderHeadersOut();
		JCoMetaData meta = table.getMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < table.getNumRows(); i++) 
		{
			table.setRow(i);
			for (int j = 0; j < meta.getFieldCount(); j++) {		
				obj.put(meta.getName(j), table.getString(j));
			}
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getStatusHeadersOutJSON(String salesOrderNumber){
		SalesDocument sd = getOrder(salesOrderNumber);
		JCoTable table = sd.getOrderStatusHeadersOut();
		JCoMetaData meta = table.getMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < table.getNumRows(); i++) 
		{
			table.setRow(i);			
			for (int j = 0; j < meta.getFieldCount(); j++) {		
				obj.put(meta.getName(j), table.getString(j));
			}
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getStatusItemsOutJSON(String salesOrderNumber){
		SalesDocument sd = getOrder(salesOrderNumber);
		JCoTable table = sd.getOrderStatusItemsOut();
		JCoMetaData meta = table.getMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < table.getNumRows(); i++) 
		{
			table.setRow(i);
			for (int j = 0; j < meta.getFieldCount(); j++) {		
				obj.put(meta.getName(j), table.getString(j));
			}
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getOrderJSON(String salesOrderNumber){
		JSONObject obj = new JSONObject();
		obj.put("ORDER_HEADERS_OUT", getHeadersOutJSON(salesOrderNumber));
		obj.put("ORDER_STATUSHEADERS_OUT", getStatusHeadersOutJSON(salesOrderNumber));
		obj.put("ORDER_STATUSITEMS_OUT", getStatusItemsOutJSON(salesOrderNumber));
		return obj;
	}
}