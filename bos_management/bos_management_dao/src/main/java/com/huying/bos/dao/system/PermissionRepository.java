package com.huying.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.huying.bos.domain.system.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

	@Query("select p from Permission p inner join p.roles r inner join r.users u where u.id=?")
	List<Permission> findbyUid(Long id);

}
