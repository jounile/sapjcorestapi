package fi.bilot;

import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoTable;
import java.util.HashMap;
import java.util.Map;

public class HelperFunctions
{
  public static String HandleLeadingZeros(String originalNumber)
  {
    if (originalNumber.length() >= 10) {
      return originalNumber;
    }

    //System.out.println("Number " + originalNumber + " was less than 10 characters. Adding leading zeros, as SAP requires it...");
    String formattedNumber = "0000000000".substring(originalNumber.length()) + originalNumber;
    //System.out.println("Number " + formattedNumber + " will be used.");
    return formattedNumber;
  }

  public static Map<String, String> singleRowTableToHashMap(JCoTable exportTable)
  {
    exportTable.setRow(0);
    JCoFieldIterator fieldIterator = exportTable.getRecordFieldIterator();

    Map singleRowTable = new HashMap();

    while (fieldIterator.hasNextField()) {
      String fieldName = fieldIterator.nextField().getName();
      String value = exportTable.getString(fieldName);

      singleRowTable.put(fieldName, value);
    }

    return singleRowTable;
  }

  public static Map<String, Map<String, String>> multiRowTableToHashMap(JCoTable exportTable)
  {
    Map multiRowTable = new HashMap();

    for (int i = 0; i < exportTable.getNumRows(); i++)
    {
      Map row = new HashMap();

      exportTable.setRow(i);
      JCoFieldIterator fieldIterator = exportTable.getRecordFieldIterator();

      while (fieldIterator.hasNextField())
      {
        String fieldName = fieldIterator.nextField().getName();
        String value = exportTable.getString(fieldName);

        row.put(fieldName, value);
      }

      multiRowTable.put("Row " + i, row);
    }

    return multiRowTable;
  }

}