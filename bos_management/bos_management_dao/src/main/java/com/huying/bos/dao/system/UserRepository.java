package com.huying.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huying.bos.domain.system.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
	User findByUsername(String username);
}
