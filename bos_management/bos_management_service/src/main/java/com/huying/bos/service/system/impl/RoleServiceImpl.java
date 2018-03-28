package com.huying.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.system.RoleRepository;
import com.huying.bos.domain.system.Role;
import com.huying.bos.service.system.RoleService;

@Transactional
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Page<Role> pageQuery(Pageable pageable) {
		
		return roleRepository.findAll(pageable);
	}

}
