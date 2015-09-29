package fi.bilot.flight;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;

public class FlightList {
	
	private String fromCountry = "DE";
	private String fromCity = "FRANKFURT";
	private String toCountry = "US";
	private String toCity = "NEW YORK";
	private String maxRead = "12";
		
	public void getFlightList() throws JCoException {
		
		JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
		JCoRepository rep = jcoDestination.getRepository();
		JCoFunction function = rep.getFunctionTemplate("BAPI_SFLIGHT_GETLIST").getFunction();
		
		JCoRecordMetaData rec = rep.getStructureDefinition("BAPISFLIST");
		
		System.out.println("Structure definition BAPISFLIST:\n");
		
		int count = rec.getFieldCount();
		for (int i = 0; i < count; i++) {
			System.out.println(i + 1 + ": " + rec.getName(i) + " " + rec.getDescription(i) + "\t");
		}
	
		function.getImportParameterList().setValue("FROMCOUNTRYKEY", fromCountry);
		function.getImportParameterList().setValue("FROMCITY", fromCity);
		function.getImportParameterList().setValue("TOCOUNTRYKEY", toCountry);
		function.getImportParameterList().setValue("TOCITY", toCity);
		function.getImportParameterList().setValue("MAXREAD", maxRead);
	
		System.out.println("\n");
		try {
			System.out.println("Calling BAPI_SFLIGHT_GETLIST" + "\n");
			function.execute(jcoDestination);
		} catch (AbapException e) {
			System.out.println(e.toString());
			return;
		}
		
		JCoTable table = function.getTableParameterList().getTable("FLIGHTLIST");
		
		System.out.println(table);
		printFlightList(rec, table);
		jsonFlightList(rec, table);
	}
	
	@SuppressWarnings("unchecked")
	public void jsonFlightList(JCoRecordMetaData rec, JCoTable table){

		JSONObject parentObj = new JSONObject();		
		for (int i = 0; i < table.getNumRows(); i++) 
		{
			table.setRow(i);
			JSONObject subObj = new JSONObject();
			for (int j = 0; j < rec.getFieldCount(); j++) {					
				subObj.put(rec.getName(j), table.getString(rec.getName(j)));
				parentObj.put(i, subObj);
			}			
		}
		System.out.print(parentObj);
	}
	
	public void printFlightList(JCoRecordMetaData rec, JCoTable table){

		System.out.println("========================================================================================");
		System.out.println("CARRID" + " || " + "CONNID" + " || " + "FLDATE" + " || " + "AIRPFROM" + " || " + "AIRPTO" + " || " + "DEPTIME" + " || " + "SEATSMAX" + " || " + "SEATSOCC" + " || ");
		System.out.println("========================================================================================");
	
		for (int i = 0; i < table.getNumRows(); i++) 
		{
			table.setRow(i);
			for (int j = 0; j < rec.getFieldCount(); j++) {		
				System.out.print(table.getString(rec.getName(j))+" || ");
			}
			System.out.println();
			System.out.println("====================================================================");
		}
		System.out.println();
	}
}
