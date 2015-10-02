package fi.bilot.pojo;


import com.sap.conn.jco.JCoTable;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SalesDocument
{
  List<JCoTable> tableList;
  String salesDocumentNumber;
  JCoTable order_headers_out;
  JCoTable order_statusheaders_out;
  JCoTable order_statusitems_out;

  public SalesDocument()
  {
    this.tableList = new ArrayList();

    this.salesDocumentNumber = null;
    this.order_headers_out = null;
    this.order_statusheaders_out = null;
    this.order_statusitems_out = null;
  }

  public void setSalesDocumentNumber(String salesDocumentNumber)
  {
    this.salesDocumentNumber = salesDocumentNumber;
  }

  public void setOrderHeadersOut(JCoTable order_hearders_out) {
    this.order_headers_out = order_hearders_out;
    this.tableList.add(this.order_headers_out);
  }

  public void setOrderStatusheadersOut(JCoTable order_statusheaders_out) {
    this.order_statusheaders_out = order_statusheaders_out;
    this.tableList.add(this.order_statusheaders_out);
  }

  public void setOrderStatusitemsOut(JCoTable order_statusitems_out) {
    this.order_statusitems_out = order_statusitems_out;
    this.tableList.add(this.order_statusitems_out);
  }

  public boolean checkIfSalesDocumentContainsData()
  {
    System.out.println("Checking if data was received...");
    Iterator iterator = this.tableList.iterator();
    System.out.println("Tables to check: " + this.tableList.size());

    while (iterator.hasNext()) {
      JCoTable table = (JCoTable)iterator.next();
      if (!table.isEmpty())
      {
        return true;
      }
    }
    return false;
  }

  public String getSalesDocumentNumber()
  {
    return this.salesDocumentNumber;
  }

  public JCoTable getOrderHeadersOut() {
    return this.order_headers_out;
  }

  public JCoTable getOrderStatusHeadersOut() {
    return this.order_statusheaders_out;
  }

  public JCoTable getOrderStatusItemsOut() {
    return this.order_statusitems_out;
  }

  public void logSalesDocument() {
    System.out.println("Sales Document number: " + this.salesDocumentNumber + "\n");
    System.out.println("ORDER_HEADERS_OUT: " + this.order_headers_out.toString() + "\n");
    System.out.println("ORDER_STATUSHEADERS_OUT: " + this.order_statusheaders_out.toString() + "\n");
    System.out.println("ORDER_STATUSITEMS_OUT: " + this.order_statusitems_out.toString() + "\n");
  }
}