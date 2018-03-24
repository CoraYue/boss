package com.huying.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.take_delivery.WayBillRepository;
import com.huying.bos.dao.take_delivery.WorkBillRepository;
import com.huying.bos.domain.take_delivery.WayBill;
import com.huying.bos.service.take_delivery.WayBillService;

@Transactional
@Service
public class WayBillServiceImpl implements WayBillService {

	@Autowired
	private WayBillRepository wayBillRepository;
	
	
	@Override
	public void save(WayBill wayBill) {
		wayBillRepository.save(wayBill);
	}


	@Override
	public Page<WayBill> pageQuery(Pageable pageable) {
		
		return wayBillRepository.findAll(pageable);
	}

}
