package fi.bilot.flight;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;

public class FlightCustomerList {
	
	private String maxRows = "10";
	private String customerName = "SAP AG";

		
	public void getFlightCustomerList() throws JCoException {
		
		JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
		JCoRepository rep = jcoDestination.getRepository();
		JCoFunction function = rep.getFunctionTemplate("BAPI_FLCUST_GETLIST").getFunction();
		
		JCoRecordMetaData rec = rep.getStructureDefinition("BAPISCUDAT");
		System.out.println("Structure definition BAPISCUDAT:\n");
		int count = rec.getFieldCount();
		for (int i = 0; i < count; i++) {
			System.out.println(i + 1 + ". " + rec.getName(i) + " " + rec.getDescription(i) + "\t");
		}
		
		JCoParameterList imports = function.getImportParameterList();
		imports.setValue("MAX_ROWS", maxRows);
		imports.setValue("CUSTOMER_NAME", customerName);
		
		System.out.println("\n Calling BAPI_FLCUST_GETLIST" + "\n");
		function.execute(jcoDestination);
		
		JCoParameterList exports = function.getTableParameterList();
		JCoTable customersTable = exports.getTable("CUSTOMER_LIST");

		xmlFlightCustomerList(customersTable);
		jsonFlightCustomerList(rec, customersTable);
	}
	
	public void xmlFlightCustomerList(JCoTable customersTable){
		System.out.println(customersTable.toXML() + "\n");
	}
	
	public void jsonFlightCustomerList(JCoRecordMetaData rec, JCoTable customersTable){
		JSONObject parentObj = new JSONObject();		
		for (int i = 0; i < customersTable.getNumRows(); i++) 
		{
			customersTable.setRow(i);
			JSONObject subObj = new JSONObject();
			for (int j = 0; j < rec.getFieldCount(); j++) {					
				subObj.put(rec.getName(j), customersTable.getString(rec.getName(j)));
				parentObj.put(i, subObj);
			}			
		}
		System.out.print(parentObj);
	}

}
