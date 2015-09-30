package fi.bilot.test;

import java.util.Date;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.monitor.JCoDestinationMonitor;

import fi.bilot.Constants;

public class Monitoring {
	
	public void getMonitoringData() throws JCoException {
		JCoDestination jcoDestination = JCoDestinationManager.getDestination(Constants.DESTINATION_NAME);
	  
		JCoDestinationMonitor monitor = jcoDestination.getMonitor();
		System.out.println("Peak Limit: " + monitor.getPeakLimit());
		System.out.println("Pool Capacity: " + monitor.getPoolCapacity());
		System.out.println("Max Used Count: " + monitor.getMaxUsedCount());
		System.out.println("Pooled Connection Count: " + monitor.getPooledConnectionCount());
		System.out.println("Used Connection Count: " + monitor.getUsedConnectionCount());
		System.out.println("Last Activity Timestamp: " + new Date(monitor.getLastActivityTimestamp()));

	}	
}
