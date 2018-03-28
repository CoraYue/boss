package com.huying.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.system.MenuRepository;
import com.huying.bos.dao.system.PermissionRepository;
import com.huying.bos.dao.system.RoleRepository;
import com.huying.bos.domain.system.Menu;
import com.huying.bos.domain.system.Permission;
import com.huying.bos.domain.system.Role;
import com.huying.bos.service.system.RoleService;

@Transactional
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MenuRepository  menuRepository ;
	
	@Autowired
	private PermissionRepository  permissionRepository ;
	
	
	@Override
	public Page<Role> pageQuery(Pageable pageable) {
		
		return roleRepository.findAll(pageable);
	}

	@Override
	public void save(Role role, String menuIds, Long[] permissionIds) {
		//根据id查找持久态对象
		roleRepository.save(role);
		
		//判断menuIds是否为空
		if(StringUtils.isNotEmpty(menuIds) ) {
			String[] split = menuIds.split(",");
			
			for (String menuId : split) {
				Menu menu = menuRepository.findOne(Long.parseLong(menuId));
				role.getMenus().add(menu);
			}
		}
		if(permissionIds != null && permissionIds.length > 0) {
			for (Long permissionId : permissionIds) {
				Permission permission = permissionRepository.findOne(permissionId);
				role.getPermissions().add(permission);
			}
		}
	}

}
