package fi.bilot.pojo;

import com.sap.conn.jco.JCoStructure;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@SuppressWarnings("restriction")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "Flight")
public class Flight {

	@XmlElement(name="carrier", required=true)
	public String carrier;
	
	@XmlElement(name="connectionNumber", required=true)
	public String connectionNumber;
	
	@XmlElement(name="date", required=true)
	public String date;
	
	JCoStructure returnStructure;
	JCoStructure flightData;


	public void setCarrier(String carrier)
	{
		this.carrier = carrier;
	}
  
	public String getCarrier()
	{
		return this.carrier;
	}

	public void setConnectionNumber(String connectionNumber) {
		this.connectionNumber = connectionNumber;
	}
	
	public String getConnectionNumber()
	{
		return this.connectionNumber;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate()
	{
		return this.date;
	}

	
	
	public JCoStructure getReturnStructure() {
		return returnStructure;
	}

	public void setReturnStructure(JCoStructure returnStructure) {
		this.returnStructure = returnStructure;
	}

	public void setFlightData(JCoStructure flightData) {
		this.flightData = flightData;
	}

	public JCoStructure getFlightData() {
		return flightData;
	}
}
