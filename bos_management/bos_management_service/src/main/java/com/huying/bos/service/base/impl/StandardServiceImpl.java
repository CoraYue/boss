package com.huying.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<Standard> pageQuery(Pageable pageable) {
		 Page<Standard> page = standardRepository.findAll(pageable);
		return page;
	}

	@Override
	public List<Standard> findAll() {
		List<Standard> list = standardRepository.findAll();
		
		return list;
	}

}
