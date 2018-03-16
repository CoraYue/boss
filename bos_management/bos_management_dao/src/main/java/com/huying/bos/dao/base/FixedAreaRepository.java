package com.huying.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.base.SubArea;

@Repository
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long>{

}
