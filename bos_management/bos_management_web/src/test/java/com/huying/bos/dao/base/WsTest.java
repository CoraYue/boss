package com.huying.bos.dao.base;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.huying.crm.domain.Customer;

public class WsTest {

	@Test
	public void testFindCustomersUnAssociated() {
		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
//		list2json(list, null);
		for (Customer customer : list) {
			System.out.println(customer);
			
		}
	}
	
}
