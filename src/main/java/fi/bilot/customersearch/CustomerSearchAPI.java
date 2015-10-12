package fi.bilot.customersearch;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;
import fi.bilot.customersearch.CustomerSearchResults;
import fi.bilot.customersearch.CustomerSearchParameters;


public class CustomerSearchAPI {

	public CustomerSearchResults getCustomerSearchResults(CustomerSearchParameters csp){
	
		CustomerSearchResults csr = new CustomerSearchResults();

		try {
			JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			JCoFunction function = jcoDestination.getRepository().getFunction("ISA_CUSTOMER_SEARCH");

			JCoParameterList importParams = function.getImportParameterList();
			importParams.setValue("CUSTOMER_NUMBER", csp.getCustomerNro());
			importParams.setValue("SALES_ORGANIZATION_RES", csp.getSalesOrg());
			importParams.setValue("DISTR_CHAN_RES", csp.getDistributionChannel());
			importParams.setValue("DIVISION_RES", csp.getDivision());
			
			System.out.println("Calling ISA_CUSTOMER_SEARCH");
			function.execute(jcoDestination);
		
			JCoTable customerSearchResults = function.getTableParameterList().getTable("CUSTOMERS_RESULTS");
			System.out.println(customerSearchResults);
		
			JCoRecordMetaData metaData = jcoDestination.getRepository().getStructureDefinition("CUSTOMER_FOUND");
			
			csr.setCustomerSearchResults(customerSearchResults);
			csr.setMetaData(metaData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return csr;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getCustomersSearchResultsJSON(CustomerSearchParameters csp) {
		CustomerSearchResults fsr = getCustomerSearchResults(csp);
		JCoTable customerSearchResults = fsr.getCustomerSearchResults();
		JCoRecordMetaData metaData = fsr.getMetaData();
		JSONObject parentObj = new JSONObject();		
		for (int i = 0; i < customerSearchResults.getNumRows(); i++) 
		{
			customerSearchResults.setRow(i);
			JSONObject subObj = new JSONObject();
			for (int j = 0; j < customerSearchResults.getFieldCount(); j++) 
			{
				subObj.put(metaData.getName(j), customerSearchResults.getString(j));
				parentObj.put(i, subObj);
			}
		}
		return parentObj;
	}
}
