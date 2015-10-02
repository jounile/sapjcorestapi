package fi.bilot.flight;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;
import fi.bilot.order.SalesDocument;
import fi.bilot.pojo.Flight;

public class FlightDetails {
	
	private String carrier = "LH";
	private String connectionNumber = "0400";
	private String date = "20151010";
	
	public Flight getFlightDetails() {
		
		JCoDestination jcoDestination;
		JCoRepository rep;
		JCoFunction function = null; 
		JCoRecordMetaData rec;		
		JCoParameterList imports;
		JCoParameterList exports;
		JCoStructure returnStructure;
		JCoStructure flightData;
		
		try {
			jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			rep = jcoDestination.getRepository();
			function = rep.getFunction("BAPI_SFLIGHT_GETDETAIL");
			rec = rep.getStructureDefinition("BAPISFDETA");
			System.out.println("Structure definition BAPISFDETA:\n");

			imports = function.getImportParameterList();
			if (carrier != null && !carrier.isEmpty()) {
				imports.setValue("AIRLINECARRIER", carrier);
			}
			if (connectionNumber != null && !connectionNumber.isEmpty()) {
				imports.setValue("CONNECTIONNUMBER", connectionNumber);
			}
			if (date != null && !date.isEmpty()) {
				imports.setValue("DATEOFFLIGHT", date);
			}

			for (int i = 0; i < rec.getFieldCount(); i++) {
				System.out.println(i + 1 + ": " + rec.getName(i) + " " + rec.getDescription(i) + "\t");
			}
			
			//System.out.println("Calling BAPI_SFLIGHT_GETDETAIL");
			function.execute(jcoDestination);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Flight flight = new Flight();
		flight.setCarrier(carrier);
		flight.setConnectionNumber(connectionNumber);
		flight.setDate(date);
		
		exports = function.getExportParameterList();
		returnStructure = exports.getStructure("RETURN");
		flight.setReturnStructure(returnStructure);
		System.out.println(returnStructure);

		flightData = exports.getStructure("FLIGHTDATA");
		flight.setFlightData(flightData);
		System.out.println(flightData);
		
		if (returnStructure.getChar(0) != 'S') {
			throw new RuntimeException(returnStructure.getString("MESSAGE"));
		}
		return flight;	
	}

	@SuppressWarnings("unchecked")
	public JSONObject getReturnStructureJSON() {
		Flight fl = getFlightDetails();
		JCoStructure returnStructure = fl.getReturnStructure();
		JCoMetaData meta = returnStructure.getMetaData();
		JSONObject obj = new JSONObject();		
		for (int i = 0; i < returnStructure.getFieldCount(); i++) 
		{
			obj.put(meta.getName(i), returnStructure.getString(i));			
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getFlightDataJSON() {
		Flight fl = getFlightDetails();
		JCoStructure returnStructure = fl.getFlightData();
		JCoMetaData meta = returnStructure.getMetaData();
		JSONObject obj = new JSONObject();		
		for (int i = 0; i < returnStructure.getFieldCount(); i++) 
		{
			obj.put(meta.getName(i), returnStructure.getString(i));			
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getFlightJSON() {
		JSONObject obj = new JSONObject();
		obj.put("RETURN", getReturnStructureJSON());
		obj.put("FLIGHTDATA", getFlightDataJSON());
		return obj;
	}
}
