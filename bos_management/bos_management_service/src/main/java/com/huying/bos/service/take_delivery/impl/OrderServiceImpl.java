package com.huying.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.AreaRepository;
import com.huying.bos.dao.base.FixedAreaRepository;
import com.huying.bos.dao.take_delivery.OrderRepository;
import com.huying.bos.dao.take_delivery.WorkBillRepository;
import com.huying.bos.domain.base.Area;
import com.huying.bos.domain.base.Courier;
import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.base.SubArea;
import com.huying.bos.domain.take_delivery.Order;
import com.huying.bos.domain.take_delivery.WorkBill;
import com.huying.bos.service.take_delivery.OrderService;

@Transactional
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Autowired
	private WorkBillRepository workBillRepository;
	
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
		//让crm系统根据发件地址查询定区ID
		//如果客户写的有数据--收件地址
		if (StringUtils.isNotEmpty(order.getSendAddress())) {
			
		
		//做下面测试之前必须在定区中关联一个客户，下单页面填写的地址必须和客户的地址一致
		String fixedAreaId= WebClient.create(
                "http://localhost:8180/crm/webService/customerService/findFixedAreaIdByAddress")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .query("address", order.getSendAddress())
                .get(String.class);
		System.out.println(fixedAreaId);
		
		//根据定区ID查询定区
		if(StringUtils.isNotEmpty(fixedAreaId)) {
			// 持久态的定区
			FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
			if (fixedArea != null) {
				   //根据定区查询快递员
				Set<Courier> couriers = fixedArea.getCouriers();
				 if ( !couriers.isEmpty() ) {
					 Iterator<Courier> iterator = couriers.iterator();
					 Courier courier = iterator.next();
					 
					 //指派快递员
					 order.setCourier(courier);
					 //生成工单
					 WorkBill workBill=new WorkBill();
					 //
					 workBill.setAttachbilltimes(0); //// 追单次数
					 workBill.setBuildtime(new Date());//工单生成时间
					 workBill.setCourier(courier); //快递员
					 
					 workBill.setOrder(order); //订单
					 workBill.setPickstate("新单"); // 取件状态
					 workBill.setRemark(order.getRemark());  //订单备注
					 workBill.setSmsNumber("001"); //短信序号
					 workBill.setType("新"); //工单类型 新,追,销
					 
					 workBillRepository.save(workBill);
					 //发送短信，推送通知告知已有新单
					 
					 //中断代码执行
					 order.setOrderType("自动分单");
					 return;
				}
			}
			
		}else {
			//客户没有输入准确的地址，输入的是关键字，要根据关键字,辅助关键字模糊查询
			//之前存进去后来取出来就变成持久态的对象了
			//做测试需要定区关联分区,在页面上填写的发件地址,必须是对应的分区的关键字或者辅助关键字
			 // 定区关联分区,在页面上填写的发件地址,必须是对应的分区的关键字或者辅助关键字
            Area sendArea2 = order.getSendArea();
            if (sendArea2 != null) {
                Set<SubArea> subareas = sendArea2.getSubareas();
                for (SubArea subArea : subareas) {
                    String keyWords = subArea.getKeyWords();
                    String assistKeyWords = subArea.getAssistKeyWords();
                    if (order.getSendAddress().contains(keyWords)
                            || order.getSendAddress().contains(assistKeyWords)) {
                    	
                        FixedArea fixedArea2 = subArea.getFixedArea();

                        if (fixedArea2 != null) {
                            // 查询快递员
                            Set<Courier> couriers =
                                    fixedArea2.getCouriers();
                            if (!couriers.isEmpty()) {
                                Iterator<Courier> iterator =
                                        couriers.iterator();
                                Courier courier = iterator.next();
                                // 指派快递员
                                order.setCourier(courier);
                                // 生成工单
                                WorkBill workBill = new WorkBill();
                                workBill.setAttachbilltimes(0);
                                workBill.setBuildtime(new Date());
                                workBill.setCourier(courier);
                                workBill.setOrder(order);
                                workBill.setPickstate("新单");
                                workBill.setRemark(order.getRemark());
                                workBill.setSmsNumber("111");
                                workBill.setType("新");

                                workBillRepository.save(workBill);
                                // 发送短信,推送一个通知
                                // 中断代码的执行
                                order.setOrderType("自动分单");
                                return;

							}
						}
					}
				}
			}
		}
		}
		//根据发件地址模糊匹配
		//人工分单--模糊查询--根据关键字查询
		order.setOrderType("人工分单");
	}

}
