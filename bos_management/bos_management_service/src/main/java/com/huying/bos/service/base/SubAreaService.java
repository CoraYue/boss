package com.huying.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.base.SubArea;

public interface SubAreaService {

	void save(SubArea model);

	Page<SubArea> pageQuery(Pageable pageable);

	List<SubArea> findUnAssociatedsubAreas();

	List<SubArea> findAssociatedSubAreas(Long fixedAreaId);

	 
}
