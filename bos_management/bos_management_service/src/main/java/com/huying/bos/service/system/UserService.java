package com.huying.bos.service.system;

import com.huying.bos.domain.system.User;

public interface UserService {

	void save(User model, long[] roleIds);

}
