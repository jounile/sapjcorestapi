package fi.bilot.flight.flightlist;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("restriction")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "FlightListSearchParameters")
public class FlightListSearchParameters {

	@XmlElement(name="fromCountry", required=true)
	public String fromCountry;
	
	@XmlElement(name="fromCity", required=true)
	public String fromCity;
	
	@XmlElement(name="toCountry", required=true)
	public String toCountry;
	
	@XmlElement(name="toCity", required=true)
	public String toCity;
	
	@XmlElement(name="maxRead", required=true)
	public String maxRead;
	
	
	public String getFromCountry() {
		return fromCountry;
	}
	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
	}
	public String getFromCity() {
		return fromCity;
	}
	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	public String getToCountry() {
		return toCountry;
	}
	public void setToCountry(String toCountry) {
		this.toCountry = toCountry;
	}
	public String getToCity() {
		return toCity;
	}
	public void setToCity(String toCity) {
		this.toCity = toCity;
	}
	public String getMaxRead() {
		return maxRead;
	}
	public void setMaxRead(String maxRead) {
		this.maxRead = maxRead;
	}

	
}
