package fi.bilot.order;

import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoTable;
import java.io.PrintStream;
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

  public static Map<String, String> collectTableFieldDomains(String tableName)
  {
    Map tableFieldDomains = new HashMap();

    if (tableName.equalsIgnoreCase("ORDER_STATUSHEADERS_OUT"))
    {
      tableFieldDomains.put("ISA_DOC_STATUS", "TEXT40");

      tableFieldDomains.put("OPERATION", "MSGFN");
      tableFieldDomains.put("SD_DOC", "VBELN");
      tableFieldDomains.put("REFDOCHDST", "STATV");
      tableFieldDomains.put("TOTREFSTAT", "STATV");
      tableFieldDomains.put("CONFIRSTAT", "STATV");
      tableFieldDomains.put("DELIV_STAT", "STATV");
      tableFieldDomains.put("DLV_STAT_H", "STATV");
      tableFieldDomains.put("TOTGOODSMV", "STATV");
      tableFieldDomains.put("BILLSTATUS", "STATV");
      tableFieldDomains.put("BILLINGSTA", "STATV");
      tableFieldDomains.put("ACCOUNTSTA", "STATV");
      tableFieldDomains.put("OVERALLREJ", "STATV");
      tableFieldDomains.put("PRC_STAT_H", "STATV");
      tableFieldDomains.put("OVERALLPIC", "STATV");
      tableFieldDomains.put("OVERALL_WM", "STATV");
      tableFieldDomains.put("TOTINCOMPL", "STATV");
      tableFieldDomains.put("TOTINCOMIT", "STATV");
      tableFieldDomains.put("TOTINCOMBI", "STATV");
      tableFieldDomains.put("GENINCOMHD", "STATV");
      tableFieldDomains.put("HDIMCOMDEL", "STATV");
      tableFieldDomains.put("HDINCOMBIL", "STATV");
      tableFieldDomains.put("DOCIMCOMPR", "STATV");
      tableFieldDomains.put("SD_DOC_CAT", "VBTYP");
      tableFieldDomains.put("SDDOCOBJ", "VBOBJ");
      tableFieldDomains.put("CH_ON", "DATUM");
      tableFieldDomains.put("BILTOTSTAT", "STATV");
      tableFieldDomains.put("INVOICSTAT", "STATV");
      tableFieldDomains.put("CUSTRESHD1", "STATV");
      tableFieldDomains.put("CUSTRESHD2", "STATV");
      tableFieldDomains.put("CUSTRESHD3", "STATV");
      tableFieldDomains.put("CUSTRESHD4", "STATV");
      tableFieldDomains.put("CUSTRESHD5", "STATV");
      tableFieldDomains.put("CUSTRESIT1", "STATV");
      tableFieldDomains.put("CUSTRESIT2", "STATV");
      tableFieldDomains.put("CUSTRESIT3", "STATV");
      tableFieldDomains.put("CUSTRESIT4", "STATV");
      tableFieldDomains.put("CUSTRESIT5", "STATV");
      tableFieldDomains.put("TOTSTATIT", "STATV_PKST");
      tableFieldDomains.put("STATSTACLC", "CMPSZ");
      tableFieldDomains.put("STATDYNCLC", "CMPSZ");
      tableFieldDomains.put("STATCREDCH", "CMPSC");
      tableFieldDomains.put("STATCRECH1", "CMPSZ");
      tableFieldDomains.put("STATCRECH2", "CMPSZ");
      tableFieldDomains.put("STATCRECH3", "CMPSZ");
      tableFieldDomains.put("STATCRECH4", "CMPSZ");
      tableFieldDomains.put("STATCRECH5", "CMPSZ");
      tableFieldDomains.put("STATCRECH6", "CMPSZ");
      tableFieldDomains.put("STATCRECH7", "CMPSZ");
      tableFieldDomains.put("STATCRECH8", "CMPSZ");
      tableFieldDomains.put("STATCRECH9", "CMPSZ");
      tableFieldDomains.put("STATCRECHA", "CMPSZ");
      tableFieldDomains.put("STATCRECHB", "CMPSZ");
      tableFieldDomains.put("STATCRECHC", "CMPSZ");
      tableFieldDomains.put("TOTSTATCCH", "CMGST");
      tableFieldDomains.put("TRNSPLANST", "TRSTA");
      tableFieldDomains.put("QUITTSTAT", "STATV");
      tableFieldDomains.put("CONFIRMSTA", "COSTA");
      tableFieldDomains.put("SAPRELEASE", "SAPRL");
      tableFieldDomains.put("TOTINCOMI", "STATV");
      tableFieldDomains.put("TOTINCOMI1", "STATV");
      tableFieldDomains.put("TOTINCOMI2", "STATV");
      tableFieldDomains.put("HDINCOMPL", "STATV");
      tableFieldDomains.put("HDINCOMPL1", "STATV");
      tableFieldDomains.put("HDINCOMPL2", "STATV");
      tableFieldDomains.put("HDINCOMPL3", "STATV");
      tableFieldDomains.put("CREDCHECK", "CMPSZ");
      tableFieldDomains.put("DELAYSTAT", "STATV");
      tableFieldDomains.put("SHIPPUNIT", "VESTK");
      tableFieldDomains.put("DISTRISTAT", "VLSTK");
      tableFieldDomains.put("REVDETSTAT", "STATV");
      tableFieldDomains.put("INDIDOCACH", "BLOCK_VB");
      tableFieldDomains.put("TOBILBLOST", "STATV");
      tableFieldDomains.put("TODELBLOST", "STATV");
      tableFieldDomains.put("TOBLOCKSTA", "STATV");
      tableFieldDomains.put("LEBHEADSTA", "STATV");
      tableFieldDomains.put("FMSTATUS", "STATV");
    }

    return tableFieldDomains;
  }
}