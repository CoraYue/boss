package com.huying.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.StandardRepository;
import com.huying.bos.domain.base.Standard;
import com.huying.bos.service.base.StandardService;

@Transactional
@Service
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardRepository standardRepository;
	
	@Override
	public void save(Standard standard) {
		standardRepository.save(standard);
	}

}
