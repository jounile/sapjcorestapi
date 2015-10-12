package fi.bilot.ordercreate;

import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.ext.Environment;

import fi.bilot.Constants;
import fi.bilot.CustomDestinationDataProvider;
import fi.bilot.customer.CustomerAPI;

@Service
@Path("/ordercreate")
public class OrderCreateService {

	@Autowired
	protected OrderCreateAPI orderCreateApi;
	
	@GET
	@Produces("application/json")
	public String createOrder() {

		return orderCreateApi.createOrder();
	}
}
