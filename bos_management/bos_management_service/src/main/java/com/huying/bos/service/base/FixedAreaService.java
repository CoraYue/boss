package com.huying.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea model);

	Page<FixedArea> pageQuery(Pageable pageable);

	void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId);

	void assignSubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds);



}
