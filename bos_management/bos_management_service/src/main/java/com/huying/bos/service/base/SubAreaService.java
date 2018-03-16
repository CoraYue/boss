package com.huying.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.base.SubArea;

public interface SubAreaService {

	void save(SubArea model);

	Page<SubArea> pageQuery(Pageable pageable);

	 
}
