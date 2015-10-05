package fi.bilot.flightlist;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;

public class FlightListAPI {
	
	public FlightListSearchResults getFlightListSearchResults(FlightListSearchParameters flsp) {
		
		JCoDestination jcoDestination;
		JCoRepository rep;
		JCoFunction function = null;
		JCoRecordMetaData rec = null;
		
		try {
			jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			rep = jcoDestination.getRepository();
			function = rep.getFunctionTemplate("BAPI_SFLIGHT_GETLIST").getFunction();
			rec = rep.getStructureDefinition("BAPISFLIST");
			
			System.out.println("Structure definition BAPISFLIST:\n");
			int count = rec.getFieldCount();
			for (int i = 0; i < count; i++) {
				System.out.println(i + 1 + ". " + rec.getName(i) + " " + rec.getDescription(i) + "\t");
			}
		
			function.getImportParameterList().setValue("FROMCOUNTRYKEY", flsp.getFromCountry());
			function.getImportParameterList().setValue("FROMCITY", flsp.getFromCity());
			function.getImportParameterList().setValue("TOCOUNTRYKEY", flsp.getToCountry());
			function.getImportParameterList().setValue("TOCITY", flsp.getToCity());
			function.getImportParameterList().setValue("MAXREAD", flsp.getMaxRead());

			System.out.println("Calling BAPI_SFLIGHT_GETLIST" + "\n");
			function.execute(jcoDestination);
		
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		JCoTable table = function.getTableParameterList().getTable("FLIGHTLIST");
		
		FlightListSearchResults flsr = new FlightListSearchResults();
		flsr.setFlightListTable(table);
		flsr.setMetaData(rec);
		
		return flsr;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getFlightListTableJSON(FlightListSearchResults flsr) {
		JCoTable table = flsr.getFlightListTable();
		JCoRecordMetaData rec = flsr.getMetaData();
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
		return parentObj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getFlightListJSON(FlightListSearchParameters flsp) {
		FlightListSearchResults flsr = getFlightListSearchResults(flsp);
		JSONObject obj = new JSONObject();
		obj.put("FLIGHTS", getFlightListTableJSON(flsr));
		return obj;
	}

}
