package fi.bilot.customer;

import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoStructure;

public class CustomerSearchResults {
	
	public JCoStructure personalData;
	public JCoStructure optionalPersonalData;
	public JCoRecordMetaData metaData;
	public JCoRecordMetaData optionalMetaData;
	
	public JCoStructure getPersonalData() {
		return personalData;
	}
	public void setPersonalData(JCoStructure personalData) {
		this.personalData = personalData;
	}
	public JCoStructure getOptionalPersonalData() {
		return optionalPersonalData;
	}
	public void setOptionalPersonalData(JCoStructure optionalPersonalData) {
		this.optionalPersonalData = optionalPersonalData;
	}
	public JCoRecordMetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(JCoRecordMetaData metaData) {
		this.metaData = metaData;
	}
	public JCoRecordMetaData getOptionalMetaData() {
		return optionalMetaData;
	}
	public void setOptionalMetaData(JCoRecordMetaData optionalMetaData) {
		this.optionalMetaData = optionalMetaData;
	}
}
