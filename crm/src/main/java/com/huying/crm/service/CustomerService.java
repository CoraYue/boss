package com.huying.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.jpa.repository.Query;

import com.huying.crm.domain.Customer;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerService {

	@GET
	@Path("/findAll")
	List<Customer> findAll();
	
	/**
	 * 查找未关联区的客户
	 */
	@GET
	@Path("findCustomersUnAssociated")
	List<Customer> findCustomersUnAssociated();
	
	@GET
	@Path("findCustomersAssociated")
	List<Customer> findCustomersAssociated(@QueryParam("fixedAreaId")String fixedAreaId);
}
