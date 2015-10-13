/*
package fi.bilot;

import java.util.Properties;     
import java.util.HashMap;  
    
import com.sap.conn.jco.JCoDestination;     
import com.sap.conn.jco.JCoDestinationManager;     
import com.sap.conn.jco.ext.DestinationDataEventListener;     
import com.sap.conn.jco.ext.DestinationDataProvider;     
import com.sap.conn.jco.ext.Environment;  
   
public class CustomDestinationDataProvider implements DestinationDataProvider {
	
    private DestinationDataEventListener eL;        
      
    private HashMap<String, Properties> destinations;  
      
    private static CustomDestinationDataProvider provider = new CustomDestinationDataProvider();  
      
    private CustomDestinationDataProvider(){  
        if( provider == null ){ 
            destinations = new HashMap();  
        }  
    }  

    public static CustomDestinationDataProvider getInstance() { 
        return provider;  
    }  
      
    public Properties getDestinationProperties(String destinationName) {     
        if( destinations.containsKey(destinationName)){  
            return destinations.get(destinationName);  
        } else {  
            throw new RuntimeException("Destination " + destinationName + " is not available");     
        }  
    }     
 
    public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {     
        this.eL = eventListener;     
    }     
 
    public boolean supportsEvents() {     
        return true;     
    }     
      
 
    void addDestination(String destinationName, Properties properties) {     
        synchronized (destinations) {  
            destinations.put(destinationName, properties);  
        }  
    }
}
*/