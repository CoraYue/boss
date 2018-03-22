package com.huying.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.base.SubArea;

@Repository
public interface SubAreaRepository extends JpaRepository<SubArea, Long>{

	//查询未关联的分区
	List<SubArea> findByFixedAreaIsNull();

	//查询关联到指定定区的分区
	List<SubArea> findByFixedArea(FixedArea fixedArea);

}
