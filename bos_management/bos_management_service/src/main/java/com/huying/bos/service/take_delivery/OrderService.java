package com.huying.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.huying.bos.domain.take_delivery.Order;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OrderService {

	//保存订单
	@POST
	@Path("/saveOrder")
	  void saveOrder(Order order);
}
