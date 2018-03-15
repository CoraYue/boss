package com.huying.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.huying.bos.domain.base.Courier;

public interface CourierService {

	void save(Courier courier);

	Page<Courier> pageQuery(Pageable pageable);

	void batchDel(String ids);

	Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

}
