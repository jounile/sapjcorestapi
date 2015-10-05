package fi.bilot.user;

import org.json.simple.JSONObject;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


import fi.bilot.Constants;


public class UserDetailsAPI
{
	User user = new User();
	
	public User getUser(String username) 
	{
		JCoDestination jcoDestination;
		JCoFunction function = null; 
		JCoParameterList importParams = null;
		JCoTable returnTable = null;
		JCoParameterList exports = null;
		JCoStructure addressData = null;
		JCoRepository rep;
		JCoRecordMetaData addressMetaData = null;	
		
		try {
			jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			function = jcoDestination.getRepository().getFunction("BAPI_USER_GET_DETAIL");

			importParams = function.getImportParameterList();
			importParams.setValue("USERNAME", username);
			importParams.setValue("CACHE_RESULTS", "X");
	      
			System.out.println("Calling BAPI_USER_GET_DETAIL");
			function.execute(jcoDestination);
			
			returnTable = function.getTableParameterList().getTable("RETURN");
			//System.out.println(returnTable);
			
			// ADDRESS structure definition
			rep = jcoDestination.getRepository();
			
			addressMetaData = rep.getStructureDefinition("BAPIADDR3");
			System.out.println("Structure definition BAPIADDR3:\n");
			for (int i = 0; i < addressMetaData.getFieldCount(); i++) {
				System.out.println(i + 1 + ": " + addressMetaData.getName(i) + " " + addressMetaData.getDescription(i) + "\t");
			}
			
			// ADDRESS structure
			exports = function.getExportParameterList();
			addressData = exports.getStructure("ADDRESS");
						
			//System.out.println(addressData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		user.setAddressData(addressData);
		user.setAddressMetaData(addressMetaData);
		
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getUserAddressDataJSON(String username) {
		User user = getUser(username);
		JCoStructure addressData = user.getAddressData();
		JCoRecordMetaData addressMetaData = user.getAddressMetaData();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < addressData.getFieldCount(); i++) 
		{
			obj.put(addressMetaData.getName(i), addressData.getString(i));
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getUserJSON(String username) {
		JSONObject obj = new JSONObject();
		obj.put("ADDRESS", getUserAddressDataJSON(username));
		return obj;
	}

}