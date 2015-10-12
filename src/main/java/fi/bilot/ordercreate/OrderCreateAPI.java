package fi.bilot.ordercreate;

import java.util.Date;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import fi.bilot.Constants;

public class OrderCreateAPI {

	@SuppressWarnings("deprecation")
	public String createOrder() {
		
		try {
			JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
			JCoFunction function = jcoDestination.getRepository().getFunction("SD_SALESDOCUMENT_CREATE");

			// Import
			
			JCoStructure sales_header_in = function.getImportParameterList().getStructure("SALES_HEADER_IN");
			sales_header_in.setValue("DOC_TYPE", "ZOR");
			sales_header_in.setValue("SALES_ORG", "0001");
			sales_header_in.setValue("DISTR_CHAN", "01");
			sales_header_in.setValue("DIVISION", "01");
			//sales_header_in.setValue("DUN_COUNT", "0");
			//sales_header_in.setValue("ADD_VAL_DY", "00");
			//sales_header_in.setValue("EXCHG_RATE", "0,00000");
			sales_header_in.setValue("REQ_DATE_H", new Date(115, 10, 12));
			//sales_header_in.setValue("CT_VALID_T", "");
			//sales_header_in.setValue("PO_METHOD", "");
			//sales_header_in.setValue("DLV_BLOCK", "");
			//sales_header_in.setValue("REF_DOC", "");
			//sales_header_in.setValue("REFDOC_CAT", "B");
			sales_header_in.setValue("PURCH_NO_C", "123456789");
			
			JCoStructure sales_header_inx = function.getImportParameterList().getStructure("SALES_HEADER_INX");			
			//sales_header_inx.setValue("ITM_NUMBER", "000010");
			//sales_header_inx.setValue("UPDATEFLAG", "I");
			//sales_header_inx.setValue("MATERIAL", "X");
			//sales_header_inx.setValue("TARGET_QTY", "X");
			
			JCoTable sales_items_in = function.getTableParameterList().getTable("SALES_ITEMS_IN");
			sales_items_in.appendRow();
			sales_items_in.setValue("ITM_NUMBER", "000010");
			sales_items_in.setValue("MATERIAL", "000000000000A10034"); // ARLA RASVATON MAITO 1L
			sales_items_in.setValue("TARGET_QTY", "1.000");
			//sales_items_in.setValue("TARGET_QU", "PC");
			sales_items_in.setValue("SALES_UNIT", "PC");
							
			JCoTable sales_schedules_in = function.getTableParameterList().getTable("SALES_SCHEDULES_IN");
			sales_schedules_in.appendRow();
			sales_schedules_in.setValue("ITM_NUMBER", "000010");
			sales_schedules_in.setValue("REQ_QTY", "1.000");
			
			JCoTable sales_partners = function.getTableParameterList().getTable("SALES_PARTNERS");
			sales_partners.appendRow();
			//sales_partners.setValue("PARTN_ROLE", "WE"); // Ship to party
			//sales_partners.setValue("PARTN_NUMB", "0000000031");
			sales_partners.setValue("PARTN_ROLE", "AG"); // Sold to party
			sales_partners.setValue("PARTN_NUMB", "0000000031");
			
			JCoTable sales_ccard = function.getTableParameterList().getTable("SALES_CCARD");
			
			JCoParameterList importParams = function.getImportParameterList();
			importParams.setValue("SALESDOCUMENT", "");
			importParams.setValue("SENDER", "");
			importParams.setValue("BINARY_RELATIONSHIPTYPE", "");
			importParams.setValue("INT_NUMBER_ASSIGNMENT", "");
			importParams.setValue("BEHAVE_WHEN_ERROR", "P");
			importParams.setValue("LOGIC_SWITCH", "");
			importParams.setValue("BUSINESS_OBJECT", "");
			importParams.setValue("TESTRUN", ""); // X to simulate order
			importParams.setValue("CONVERT_PARVW_AUART", "");
			importParams.setValue("STATUS_BUFFER_REFRESH", "X");
			

			System.out.println("Calling SD_SALESDOCUMENT_CREATE");
			function.execute(jcoDestination);
			
			// Export
			JCoParameterList exportParams = function.getExportParameterList();
			String vbeln = (String) exportParams.getValue("SALESDOCUMENT_EX");
			System.out.println("VBELN: " + vbeln);
			
			JCoStructure sales_header_out = function.getExportParameterList().getStructure("SALES_HEADER_OUT");
			//System.out.println(sales_header_out);
			
			JCoStructure sales_header_status = function.getExportParameterList().getStructure("SALES_HEADER_STATUS");
			//System.out.println(sales_header_status);
			
			// Tables
			JCoTable returnTable = function.getTableParameterList().getTable("RETURN");
			
			if (returnTable.getChar(0) != 'S') {
				throw new RuntimeException(returnTable.getString("MESSAGE"));
			}
			
			JCoTable items_ex = function.getTableParameterList().getTable("ITEMS_EX");
			//System.out.println(items_ex);
			
			JCoTable schedule_ex = function.getTableParameterList().getTable("SCHEDULE_EX");
			//System.out.println(schedule_ex);
			
			JCoTable sales_text = function.getTableParameterList().getTable("SALES_TEXT");
			//System.out.println(sales_text);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "success/fail";
	}
	
	public String simulateOrder() {
		return "success/fail";
	}

}
