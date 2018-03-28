package com.huying.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.system.PermissionRepository;
import com.huying.bos.domain.system.Menu;
import com.huying.bos.domain.system.Permission;
import com.huying.bos.service.system.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public Page<Permission> pageQuery(Pageable pageable) {
		
		return permissionRepository.findAll(pageable);
	}

	@Override
	public void save(Permission model) {
		permissionRepository.save(model);
		
	}

}
