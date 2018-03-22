package com.huying.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.SubAreaRepository;
import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.base.SubArea;
import com.huying.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaRepository subAreaRepository;
	
	public void save(SubArea subArea) {
		subAreaRepository.save(subArea);
	}

	@Override
	public Page<SubArea> pageQuery(Pageable pageable) {
		Page<SubArea> page = subAreaRepository.findAll(pageable);
		return page;
	}

	//关联未关联定区的分区
	@Override
	public List<SubArea> findUnAssociatedsubAreas() {
	
		return subAreaRepository.findByFixedAreaIsNull();
	}

	//查询关联到指定分区的分区
	@Override
	public List<SubArea> findAssociatedSubAreas(Long fixedAreaId) {
		
		  FixedArea fixedArea = new FixedArea();

	        fixedArea.setId(fixedAreaId);
		return subAreaRepository.findByFixedArea(fixedArea);
	}
	
	
}
