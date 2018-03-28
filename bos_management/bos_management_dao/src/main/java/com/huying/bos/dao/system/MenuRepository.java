package com.huying.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huying.bos.domain.system.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{

	List<Menu> findByParentMenuIsNull();

}
