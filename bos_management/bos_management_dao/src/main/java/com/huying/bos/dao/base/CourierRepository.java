package com.huying.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huying.bos.domain.base.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long>{

	@Modifying
	@Query("update Courier set deltag = 1 where id=?")
	void updateDelTagById(long parseLong);

	

}
