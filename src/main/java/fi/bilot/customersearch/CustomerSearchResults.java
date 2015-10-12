package fi.bilot.customersearch;

import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoTable;

public class CustomerSearchResults {
	
	public JCoTable customerSearchResults;
	public JCoRecordMetaData metaData;
	
	public JCoTable getCustomerSearchResults() {
		return customerSearchResults;
	}

	public void setCustomerSearchResults(JCoTable customerSearchResults) {
		this.customerSearchResults = customerSearchResults;		
	}
	
	public JCoRecordMetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(JCoRecordMetaData metaData) {
		this.metaData = metaData;
	}	
}
