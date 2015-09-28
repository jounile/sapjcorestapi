package fi.bilot;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

import fi.bilot.Constants;

public class JcoConnection {
	
    static 
	{
		CustomDestinationDataProvider provider = CustomDestinationDataProvider.getInstance(); 
		Environment.registerDestinationDataProvider(provider);
        
        Properties connectProperties = new Properties(); 
        connectProperties.setProperty(DestinationDataProvider.JCO_DEST, "BE2"); 
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "biloterp2.bilot.local");     
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");     
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");     
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "hybris_rfc");     
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "init1234");   
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "en");
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "100");
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");
             
        createDestinationDataFile(Constants.DESTINATION_NAME, connectProperties);
        
	}
	
    static void createDestinationDataFile(String destinationName, Properties connectProperties)
    {
        File destCfg = new File(destinationName+".jcoDestination");
        try
        {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to create the destination files", e);
        }

    }
}
