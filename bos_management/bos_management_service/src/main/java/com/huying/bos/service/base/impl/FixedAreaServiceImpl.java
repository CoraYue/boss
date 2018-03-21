package com.huying.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.CourierRepository;
import com.huying.bos.dao.base.FixedAreaRepository;
import com.huying.bos.dao.base.TakeTimeRepository;
import com.huying.bos.domain.base.Courier;
import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.base.TakeTime;
import com.huying.bos.service.base.CourierService;
import com.huying.bos.service.base.FixedAreaService;
import com.huying.bos.service.base.TakeTimeService;
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Autowired
	private CourierRepository courierRepository;
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	
	@Override
	public void save(FixedArea fixedArea) {
		fixedAreaRepository.save(fixedArea);
	}

	@Override
	public Page<FixedArea> pageQuery(Pageable pageable) {
		return fixedAreaRepository.findAll(pageable);
	}

	@Override
	public void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId) {
		//根据id查找对应的定区
		FixedArea fixedArea= fixedAreaRepository.findOne(fixedAreaId);
		//查找对应的快递员
	      Courier courier=  courierRepository.findOne(courierId);
	      //查找时间
	      TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
	      
	      //建立快递员和时间的关联
	      courier.setTakeTime(takeTime);
	      //建立快递员和定区的关联
	      fixedArea.getCouriers().add(courier);
	}

}
