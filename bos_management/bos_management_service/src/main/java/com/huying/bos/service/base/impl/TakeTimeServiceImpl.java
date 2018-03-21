package com.huying.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.TakeTimeRepository;
import com.huying.bos.domain.base.TakeTime;
import com.huying.bos.service.base.TakeTimeService;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

	@Autowired
	private TakeTimeRepository takeTimeRepository; 
	
	@Override
	public List<TakeTime> findAll(){
		return takeTimeRepository.findAll();
	}
}
