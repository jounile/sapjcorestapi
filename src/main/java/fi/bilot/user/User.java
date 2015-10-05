package fi.bilot.user;

import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoStructure;

public class User {
	
	public String username;
	
	public JCoStructure addressData;
	public JCoRecordMetaData addressMetaData;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
	
	public JCoStructure getAddressData() {
		return addressData;
	}

	public void setAddressData(JCoStructure addressData) {
		this.addressData = addressData;
	}

	public JCoRecordMetaData getAddressMetaData() {
		return addressMetaData;
	}
	
	public void setAddressMetaData(JCoRecordMetaData addressMetaData) {
		this.addressMetaData = addressMetaData;
	}

	
}
