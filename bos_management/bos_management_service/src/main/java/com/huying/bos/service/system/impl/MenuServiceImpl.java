package com.huying.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.system.MenuRepository;
import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.system.Menu;
import com.huying.bos.domain.system.User;
import com.huying.bos.service.system.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuRepository menuRepository;
	
	//查询所有菜单分类
	@Override
	public List<Menu> findLevelOne() {
		return menuRepository.findByParentMenuIsNull();
	}

	//保存菜单
	@Override
	public void save(Menu model) {
		// 判断用户是否要添加一级菜单,父菜单是否id为null
		Menu parentMenu = model.getParentMenu();
		if (parentMenu != null && parentMenu.getId() == null ) {
			model.setParentMenu(null);
		}
		menuRepository.save(model);
	}

	@Override
	public Page<Menu> pageQuery(Pageable pageable) {
		return menuRepository.findAll(pageable);
	}

	@Override
	public List<Menu> findbyUser(User user) {
		if("admin".equals(user.getUsername())) {
			return menuRepository.findAll();
		}
		return menuRepository.findbyUser(user.getId());
	}

}
