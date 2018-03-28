package com.huying.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huying.bos.domain.system.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
