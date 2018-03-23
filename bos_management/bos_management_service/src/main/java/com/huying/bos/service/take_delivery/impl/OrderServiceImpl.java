package com.huying.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.AreaRepository;
import com.huying.bos.dao.take_delivery.OrderRepository;
import com.huying.bos.domain.base.Area;
import com.huying.bos.domain.take_delivery.Order;
import com.huying.bos.service.take_delivery.OrderService;

@Transactional
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Override
	public void saveOrder(Order order) {
		
		//把瞬时态的Area转换为持久态的Area，此时是游离态
		//寄件地址转换持久态
		Area sendArea=order.getSendArea();
		if (sendArea != null) {
			//根据省市区查找持久态，并重新赋值给order对象
			Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
			order.setSendArea(sendAreaDB);
		}
		//收件地址转换持久态
		Area recArea=order.getRecArea();
		if (recArea != null) {
			//根据省市区查找持久态，并重新赋值给order对象
			Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());
			order.setRecArea(recAreaDB);
		}
		//设置下单时间，订单编号
		order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
		order.setOrderTime(new Date());
		orderRepository.save(order);
		
		//自动分单
		//根据发件地址完全匹配
		//根据发件地址模糊匹配
		//人工分单--模糊查询--根据关键字查询
		
	}

}
