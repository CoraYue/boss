package com.huying.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.system.User;

public interface UserService {

	void save(User model, long[] roleIds);

	Page<User> pageQuery(Pageable pageable);

}
