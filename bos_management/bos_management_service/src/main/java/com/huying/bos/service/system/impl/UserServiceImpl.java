package com.huying.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.system.UserRepository;
import com.huying.bos.domain.system.Role;
import com.huying.bos.domain.system.User;
import com.huying.bos.service.system.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void save(User user, long[] roleIds) {
		userRepository.save(user);
		
		if (roleIds != null && roleIds.length>0) {
			for (long roleId : roleIds) {
				Role role=new Role();
				role.setId(roleId);
				user.getRoles().add(role);
			}
		}
	}

}
