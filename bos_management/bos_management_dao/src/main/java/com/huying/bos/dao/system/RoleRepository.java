package com.huying.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.huying.bos.domain.system.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	@Query("select r from Role r inner join r.users u where u.id=?")
	List<Role> findbyUid(Long id); 

}
