package com.huying.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.system.Permission;

public interface PermissionService {

	Page<Permission> pageQuery(Pageable pageable);

	void save(Permission model);


	
}
