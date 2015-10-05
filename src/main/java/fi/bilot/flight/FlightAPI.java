package fi.bilot.flight;

import org.json.simple.JSONObject;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;
import fi.bilot.flightlist.FlightListSearchParameters;
import fi.bilot.flightlist.FlightListSearchResults;

public class FlightAPI {
	
	public FlightSearchResults getFlightSearchResults(FlightSearchParameters fsp) {
		
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
			if (fsp.getCarrier() != null && !fsp.getCarrier().isEmpty()) {
				imports.setValue("AIRLINECARRIER", fsp.getCarrier());
			}
			if (fsp.getConnectionNumber() != null && !fsp.getConnectionNumber().isEmpty()) {
				imports.setValue("CONNECTIONNUMBER", fsp.getConnectionNumber());
			}
			if (fsp.getDate() != null && !fsp.getDate().isEmpty()) {
				imports.setValue("DATEOFFLIGHT", fsp.getDate());
			}

			for (int i = 0; i < rec.getFieldCount(); i++) {
				System.out.println(i + 1 + ": " + rec.getName(i) + " " + rec.getDescription(i) + "\t");
			}
			
			//System.out.println("Calling BAPI_SFLIGHT_GETDETAIL");
			function.execute(jcoDestination);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FlightSearchResults fsr = new FlightSearchResults();
		exports = function.getExportParameterList();
		returnStructure = exports.getStructure("RETURN");
		fsr.setReturnStructure(returnStructure);
		System.out.println(returnStructure);

		flightData = exports.getStructure("FLIGHTDATA");
		fsr.setFlightData(flightData);
		System.out.println(flightData);
		
		if (returnStructure.getChar(0) != 'S') {
			throw new RuntimeException(returnStructure.getString("MESSAGE"));
		}
		
		return fsr;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getReturnStructureJSON(FlightSearchResults fsr) {		
		JCoStructure returnStructure = fsr.getReturnStructure();
		JCoMetaData meta = returnStructure.getMetaData();
		JSONObject obj = new JSONObject();		
		for (int i = 0; i < returnStructure.getFieldCount(); i++) 
		{
			obj.put(meta.getName(i), returnStructure.getString(i));			
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getFlightDataJSON(FlightSearchResults fsr) {		
		JCoStructure returnStructure = fsr.getFlightData();
		JCoMetaData meta = returnStructure.getMetaData();
		JSONObject obj = new JSONObject();		
		for (int i = 0; i < returnStructure.getFieldCount(); i++) 
		{
			obj.put(meta.getName(i), returnStructure.getString(i));			
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getFlightJSON(FlightSearchParameters fsp) {
		FlightSearchResults fsr = getFlightSearchResults(fsp);
		JSONObject obj = new JSONObject();
		obj.put("RETURN", getReturnStructureJSON(fsr));
		obj.put("FLIGHTDATA", getFlightDataJSON(fsr));
		return obj;
	}
}
