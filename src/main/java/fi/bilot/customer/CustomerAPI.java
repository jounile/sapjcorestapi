package fi.bilot.customer;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoStructure;

import fi.bilot.Constants;
import fi.bilot.HelperFunctions;

public class CustomerAPI
{
	
	public CustomerSearchResults getCustomerSearchResults(CustomerSearchParameters csp) 
	{
		CustomerSearchResults csr = new CustomerSearchResults();

		try {
			JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			JCoFunction function = jcoDestination.getRepository().getFunction("BAPI_CUSTOMER_GETDETAIL1");

			JCoParameterList importParams = function.getImportParameterList();
			importParams.setValue("CUSTOMERNO", HelperFunctions.HandleLeadingZeros(csp.getCustomerNro()));
			importParams.setValue("PI_SALESORG", csp.getSalesOrg());
			importParams.setValue("PI_DISTR_CHAN", csp.getDistributionChannel());
			importParams.setValue("PI_DIVISION", csp.getDivision());
			

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
			
			csr.setPersonalData(personalData);
			csr.setOptionalPersonalData(optionalPersonalData);
			csr.setMetaData(metaData);
			csr.setOptionalMetaData(optionalMetaData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return csr;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getPersonalDataJSON(CustomerSearchResults fsr) {
		JCoStructure personalData = fsr.getPersonalData();
		JCoRecordMetaData metaData = fsr.getMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < personalData.getFieldCount(); i++) 
		{
			obj.put(metaData.getName(i), personalData.getString(i));
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getOptionalPersonalDataJSON(CustomerSearchResults fsr) {
		JCoStructure optionalPersonalData = fsr.getOptionalPersonalData();
		JCoRecordMetaData optionalMetaData = fsr.getOptionalMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < optionalPersonalData.getFieldCount(); i++) 
		{
			obj.put(optionalMetaData.getName(i), optionalPersonalData.getString(i));
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getCustomerJSON(CustomerSearchParameters csp) {
		CustomerSearchResults fsr = getCustomerSearchResults(csp);
		JSONObject obj = new JSONObject();
		obj.put("PE_PERSONALDATA", getPersonalDataJSON(fsr));
		obj.put("PE_OPT_PERSONALDATA", getOptionalPersonalDataJSON(fsr));
		return obj;
	}
}