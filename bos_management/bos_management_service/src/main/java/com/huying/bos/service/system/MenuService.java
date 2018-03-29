package com.huying.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.system.Menu;
import com.huying.bos.domain.system.User;

public interface MenuService {

	List<Menu> findLevelOne();

	void save(Menu model);

	Page<Menu> pageQuery(Pageable pageable);

	List<Menu> findbyUser(User user);

}
