package fi.bilot.customer;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;
import fi.bilot.HelperFunctions;
import fi.bilot.user.User;

public class CustomerAPI
{
	Customer customer = new Customer();
	
	public Customer getCustomer(String customerNro) 
	{

		try {
			JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			JCoFunction function = jcoDestination.getRepository().getFunction("BAPI_CUSTOMER_GETDETAIL1");

			customerNro = HelperFunctions.HandleLeadingZeros(customerNro);
			
			JCoParameterList importParams = function.getImportParameterList();
			importParams.setValue("CUSTOMERNO", customerNro);
			importParams.setValue("PI_SALESORG", "0001");
			importParams.setValue("PI_DISTR_CHAN", "01");
			importParams.setValue("PI_DIVISION", "01");
			

			System.out.println("Calling BAPI_CUSTOMER_GETDETAIL1");
			function.execute(jcoDestination);
			
			JCoParameterList exports = function.getExportParameterList();
			JCoStructure returnTable = exports.getStructure("RETURN");
			System.out.println(returnTable);
			
			JCoStructure personalData = exports.getStructure("PE_PERSONALDATA");
			JCoStructure optionalPersonalData = exports.getStructure("PE_OPT_PERSONALDATA");

			JCoRecordMetaData metaData = jcoDestination.getRepository().getStructureDefinition("BAPIKNA101_1");
			System.out.println("Structure definition BAPIKNA101_1:\n");
			for (int i = 0; i < metaData.getFieldCount(); i++) {
				System.out.println(i + 1 + ": " + metaData.getName(i) + " " + metaData.getDescription(i) + "\t");
			}
			
			JCoRecordMetaData optionalMetaData = jcoDestination.getRepository().getStructureDefinition("BAPIKNA105");
			System.out.println("Structure definition BAPIKNA105:\n");
			for (int i = 0; i < optionalMetaData.getFieldCount(); i++) {
				System.out.println(i + 1 + ": " + optionalMetaData.getName(i) + " " + optionalMetaData.getDescription(i) + "\t");
			}
			
			customer.setPersonalData(personalData);
			customer.setOptionalPersonalData(optionalPersonalData);
			customer.setMetaData(metaData);
			customer.setOptionalMetaData(optionalMetaData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return customer;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getPersonalDataJSON(String customerNro) {
		Customer customer = getCustomer(customerNro);
		JCoStructure personalData = customer.getPersonalData();
		JCoRecordMetaData metaData = customer.getMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < personalData.getFieldCount(); i++) 
		{
			obj.put(metaData.getName(i), personalData.getString(i));
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getOptionalPersonalDataJSON(String customerNro) {
		JCoStructure optionalPersonalData = customer.getOptionalPersonalData();
		JCoRecordMetaData optionalMetaData = customer.getOptionalMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < optionalPersonalData.getFieldCount(); i++) 
		{
			obj.put(optionalMetaData.getName(i), optionalPersonalData.getString(i));
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getCustomerJSON(String customerNro) {
		JSONObject obj = new JSONObject();
		obj.put("PE_PERSONALDATA", getPersonalDataJSON(customerNro));
		obj.put("PE_OPT_PERSONALDATA", getOptionalPersonalDataJSON(customerNro));
		return obj;
	}
}