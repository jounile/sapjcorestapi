package fi.bilot.flight;

import org.json.simple.JSONObject;

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

public class FlightDetails {
	
	private String carrier = "LH";
	private String connectionNumber = "0400";
	private String date = "20151010";
	
	public void getFlightDetails() {
		
		try {
			JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			JCoRepository rep = jcoDestination.getRepository();
			JCoFunction function = jcoDestination.getRepository().getFunction("BAPI_SFLIGHT_GETDETAIL");

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
			
			function.execute(jcoDestination);

			JCoParameterList exports = function.getExportParameterList();
			JCoStructure returnStructure = exports.getStructure("RETURN");
			JCoStructure flightData = exports.getStructure("FLIGHTDATA");
			
			//System.out.println(exports);
			//System.out.println(returnStructure);
			System.out.println(flightData);

			if (returnStructure.getChar(0) != 'S') {
				throw new RuntimeException(returnStructure.getString("MESSAGE"));
			} else {
				//printFlightDetails(flightData);
				//xmlFlightDetails(flightData);
				jsonFlightDetails(flightData);
			}
		} catch (JCoException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void printFlightDetails(JCoStructure flightData){

		for (int i = 0; i < flightData.getMetaData().getFieldCount(); i++) 
		{
			System.out.println(flightData.getMetaData().getName(i) + ":\t" + flightData.getString(i));
		}
		
		for(JCoField field : flightData)
        {
            System.out.println(field.getName() + ":\t" + field.getString());
        }
	}
	
	public void xmlFlightDetails(JCoStructure flightData){
		System.out.println(flightData.toXML() + "\n");
	}
	
	public void jsonFlightDetails(JCoStructure flightData){
		JSONObject obj = new JSONObject();		
		for (int i = 0; i < flightData.getFieldCount(); i++) 
		{
			obj.put(flightData.getMetaData().getName(i), flightData.getString(i));			
		}
		System.out.println(obj);
	}
}
