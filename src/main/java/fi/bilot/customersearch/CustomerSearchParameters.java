package fi.bilot.customersearch;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("restriction")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "CustomerSearchParameters")
public class CustomerSearchParameters {
	
	@XmlElement(name="customerNro", required=true)
	public String customerNro;
	
	@XmlElement(name="salesOrg", required=true)
	public String salesOrg;
	
	@XmlElement(name="distributionChannel", required=true)
	public String distributionChannel;
	
	@XmlElement(name="division", required=true)
	public String division;
	
	public String getCustomerNro() {
		return customerNro;
	}
	public void setCustomerNro(String customerNro) {
		this.customerNro = customerNro;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getDistributionChannel() {
		return distributionChannel;
	}
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	
	
}
