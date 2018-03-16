package com.huying.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.FixedAreaRepository;
import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.service.base.FixedAreaService;
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Override
	public void save(FixedArea fixedArea) {
		fixedAreaRepository.save(fixedArea);
	}

	@Override
	public Page<FixedArea> pageQuery(Pageable pageable) {
		return fixedAreaRepository.findAll(pageable);
	}

}
