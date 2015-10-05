package fi.bilot.flight;

import com.sap.conn.jco.JCoStructure;

public class FlightSearchResults {

	
	JCoStructure returnStructure;
	JCoStructure flightData;
	
	public JCoStructure getReturnStructure() {
		return returnStructure;
	}

	public void setReturnStructure(JCoStructure returnStructure) {
		this.returnStructure = returnStructure;
	}

	public void setFlightData(JCoStructure flightData) {
		this.flightData = flightData;
	}

	public JCoStructure getFlightData() {
		return flightData;
	}
}
