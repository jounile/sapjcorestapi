/*
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
        connectProperties.setProperty(DestinationDataProvider.JCO_DEST, ""); 
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "");     
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "");     
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "");     
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "");     
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "");   
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "");
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "");
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "");
             
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
*/