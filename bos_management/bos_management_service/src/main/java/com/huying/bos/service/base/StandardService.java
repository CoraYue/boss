package com.huying.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.base.Standard;

public interface StandardService {

	void save(Standard standard);

	Page<Standard> pageQuery(Pageable pageable);

	List<Standard> findAll();

}
