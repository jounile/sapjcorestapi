package fi.bilot.order;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import fi.bilot.order.SalesDocument;
import java.io.PrintStream;

public class OrderJcoFunctionCalls
{

  public static SalesDocument retrieveSalesDocumentFromErp(String salesOrderNumber)
    throws JCoException
  {
    JCoDestination destination = JCoDestinationManager.getDestination("ABAP_AS");

    JCoFunction function = destination.getRepository().getFunction("BAPI_ISAORDER_GETDETAILEDLIST");
    if (function == null) {
      throw new RuntimeException("Function BAPI_ISAORDER_GETDETAILEDLIST not found in SAP.");
    }

    JCoStructure importStructure = function.getImportParameterList().getStructure("I_BAPI_VIEW");

    importStructure.setValue("HEADER", "X");
    importStructure.setValue("STATUS_H", "X");
    importStructure.setValue("STATUS_I", "X");

    JCoTable importTable = function.getTableParameterList().getTable("SALES_DOCUMENTS");

    importTable.appendRow();

    salesOrderNumber = HelperFunctions.HandleLeadingZeros(salesOrderNumber);
    importTable.setValue("VBELN", salesOrderNumber);
    try
    {
      function.execute(destination);
    } catch (AbapException e) {
      System.out.println(e.toString());
      throw new Error("FAIL: Error occured while executing function BAPI_ISAORDER_GETDETAILEDLIST");
    }

    System.out.println("BAPI_ISAORDER_GETDETAILEDLIST finished:");

    SalesDocument salesDocument = new SalesDocument();
    salesDocument.setSalesDocumentNumber(salesOrderNumber);

    if (importStructure.getValue("HEADER").toString().equalsIgnoreCase("X")) {
      JCoTable exportTable = null;
      exportTable = function.getTableParameterList().getTable("ORDER_HEADERS_OUT");
      salesDocument.setOrderHeadersOut(exportTable);
    }
    if (importStructure.getValue("STATUS_H").toString().equalsIgnoreCase("X")) {
      JCoTable exportTable = null;
      exportTable = function.getTableParameterList().getTable("ORDER_STATUSHEADERS_OUT");
      salesDocument.setOrderStatusheadersOut(exportTable);
    }
    if (importStructure.getValue("STATUS_I").toString().equalsIgnoreCase("X")) {
      JCoTable exportTable = null;
      exportTable = function.getTableParameterList().getTable("ORDER_STATUSITEMS_OUT");
      salesDocument.setOrderStatusitemsOut(exportTable);
    }

    return salesDocument;
  }
}