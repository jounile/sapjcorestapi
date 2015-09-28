package fi.bilot.flight;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class FlightJcoFunctionCalls {
	
	private String fromCountry = "DE";
	private String fromCity = "FRANKFURT";
	private String toCountry = "US";
	private String toCity = "NEW YORK";
	private String maxRead = "12";
	
	private String carrier = "LH";
	private String connectionNumber = "0400";
	private String date = "20151010";
	
	public void getFlightList(JCoDestination destination) throws JCoException {
		
		JCoRepository rep = null;
		JCoFunction function = null;

		rep = destination.getRepository();
		function = rep.getFunctionTemplate("BAPI_SFLIGHT_GETLIST").getFunction();
		
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
			function.execute(destination);
		} catch (AbapException e) {
			System.out.println(e.toString());
			return;
		}
		
		JCoTable table = function.getTableParameterList().getTable("FLIGHTLIST");

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
	}
	
	public void getFlightDetails(JCoDestination destination) {
		
		try {
			JCoRepository rep = destination.getRepository();
			JCoFunction function = destination.getRepository().getFunction("BAPI_SFLIGHT_GETDETAIL");

			JCoParameterList imports = function.getImportParameterList();
			if (carrier != null && !carrier.isEmpty()) {
				imports.setValue("AIRLINECARRIER", carrier);
			}
			if (connectionNumber != null && !connectionNumber.isEmpty()) {
				imports.setValue("CONNECTIONNUMBER", connectionNumber);
			}
			if (date != null && !date.isEmpty()) {
				imports.setValue("DATEOFFLIGHT", date);
			}
			
			JCoRecordMetaData rec = rep.getStructureDefinition("BAPISFDETA");
			
			System.out.println("Structure definition BAPISFDETA:\n");
			
			int count = rec.getFieldCount();
			for (int i = 0; i < count; i++) {
				System.out.println(i + 1 + ": " + rec.getName(i) + " " + rec.getDescription(i) + "\t");
			}
			
			function.execute(destination);

			JCoParameterList exports = function.getExportParameterList();
			JCoStructure returnStructure = exports.getStructure("RETURN");
			JCoStructure flightData = exports.getStructure("FLIGHTDATA");

			if (returnStructure.getChar(0) != 'S') {
				throw new RuntimeException(returnStructure.getString("MESSAGE"));
			} else {
				//System.out.println(flightData.toXML() + "\n");

				System.out.println();
				for (int i = 0; i < flightData.getMetaData().getFieldCount(); i++) 
				{
					System.out.println(flightData.getMetaData().getName(i) + ":\t" + flightData.getString(i));
				}
				
				for(JCoField field : flightData)
		        {
		            System.out.println(field.getName() + ":\t" + field.getString());
		        }
			}
		} catch (JCoException e) {
			throw new RuntimeException(e);
		}
	}
}
