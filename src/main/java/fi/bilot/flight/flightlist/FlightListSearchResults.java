package fi.bilot.flight.flightlist;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
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


public class FlightListSearchResults {

	public JCoTable table;
	public JCoRecordMetaData rec;
	
	public void setFlightListTable(JCoTable table) {
		this.table = table;
	}

	public JCoTable getFlightListTable() {
		return table;
	}

	public void setMetaData(JCoRecordMetaData rec) {
		this.rec = rec;
	}
	
	public JCoRecordMetaData getMetaData(){
		return rec;
	}
		

}
