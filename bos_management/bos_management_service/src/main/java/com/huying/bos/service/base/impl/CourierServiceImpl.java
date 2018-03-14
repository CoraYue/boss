package com.huying.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.CourierRepository;
import com.huying.bos.domain.base.Courier;
import com.huying.bos.service.base.CourierService;

@Transactional
@Service
public class CourierServiceImpl implements CourierService{

	@Autowired
	private CourierRepository courierRepository;
	
	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);
	}

}
