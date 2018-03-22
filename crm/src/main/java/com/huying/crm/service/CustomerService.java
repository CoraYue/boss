package com.huying.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	List<Customer> findCustomersAssociated(@QueryParam("fixedAreaId") String fixedAreaId);

	@PUT
	@Path("/assignCustomers2FixedArea")
	void assignCustomers2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId,
			@QueryParam("customerIds") Long[] customerIds);

	@PUT
	@Path("/assignCustomers2FixedArea2")
	void assignCustomers2FixedArea2(@QueryParam("uncustomerIds") Long[] uncustomerIds);

	// 注册
  @POST
  @Path("/save")
  void save(Customer customer);
  
  //激活用户
  @PUT
  @Path("/active")
   void active(@QueryParam("telephone")String telephone);
  
  //校验用户是否激活
  @GET
  @ Path("/isActived")
  Customer isActived(@QueryParam("telephone")String telephone);
  
  //登录
  @GET
  @Path("/login")
  Customer login(@QueryParam("telephone")String telephone , @QueryParam("password")String password);
}
