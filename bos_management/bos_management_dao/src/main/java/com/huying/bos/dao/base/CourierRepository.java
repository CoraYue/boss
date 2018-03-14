package com.huying.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huying.bos.domain.base.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long>{

	

}
