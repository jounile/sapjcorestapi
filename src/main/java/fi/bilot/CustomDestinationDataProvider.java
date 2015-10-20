package fi.bilot;

import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class CustomDestinationDataProvider
{
    public static class MyDestinationDataProvider implements DestinationDataProvider
    {
        private DestinationDataEventListener eL;

        private Properties ABAP_AS_WITH_POOL_properties; 
        
        public Properties getDestinationProperties(String destinationName)
        {
            if(destinationName.equals("ABAP_AS_WITH_POOL") && ABAP_AS_WITH_POOL_properties!=null)
                return ABAP_AS_WITH_POOL_properties;
            
            return null;
            //alternatively throw runtime exception
            //throw new RuntimeException("Destination " + destinationName + " is not available");
        }

        public void setDestinationDataEventListener(DestinationDataEventListener eventListener)
        {
            this.eL = eventListener;
        }

        public boolean supportsEvents()
        {
            return true;
        }
        
        public void changePropertiesForABAP_AS_WITH_POOL(Properties properties)
        {
            if(properties==null)
            {
                ABAP_AS_WITH_POOL_properties = null;
                eL.deleted("ABAP_AS_WITH_POOL");
            }
            else 
            {
                if(ABAP_AS_WITH_POOL_properties==null || !ABAP_AS_WITH_POOL_properties.equals(properties))
                {
	                ABAP_AS_WITH_POOL_properties = properties;
                    eL.updated("ABAP_AS_WITH_POOL");
                }
            }
        }
    }
}