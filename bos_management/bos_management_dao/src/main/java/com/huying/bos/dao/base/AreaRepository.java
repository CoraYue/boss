package com.huying.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huying.bos.domain.base.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long>{

}
