package com.huying.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.system.Role;

public interface RoleService {

	Page<Role> pageQuery(Pageable pageable);

	void save(Role model, String menuIds, Long[] permissionIds);

}
