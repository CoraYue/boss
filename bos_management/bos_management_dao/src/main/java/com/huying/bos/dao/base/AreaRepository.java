package com.huying.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huying.bos.domain.base.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{

	@Query("from Area where province like ?1 or city like ?1 or district like ?1 or postcode like ?1 or citycode like ?1 or shortcode like ?1  ")
	List<Area> findByQ(String q);

}
