package com.huying.bos.service.base.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.base.CourierRepository;
import com.huying.bos.domain.base.Courier;
import com.huying.bos.service.base.CourierService;

@Transactional
@Service
public class CourierServiceImpl implements CourierService{

	@Autowired
	private CourierRepository courierRepository;
	
	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);
	}

	@Override
	public Page<Courier> pageQuery(Pageable pageable) {
		
		return  courierRepository.findAll(pageable);
		
	}

	//批量删除
	@RequiresPermissions("batchDel")
	@Override
	public void batchDel(String ids) {
		//为空判断
		if(StringUtils.isNotEmpty(ids)) {
			//切割数据
			String[] split = ids.split(",");
			for (String id : split) {
				courierRepository.updateDelTagById(Long.parseLong(id));
			}
		}
		
	}

	@Override
	public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
		return  courierRepository.findAll(specification, pageable);
		
	}

	//还原快递员
	@Override
	public void rest(String ids) {
		//为空判断
				if(StringUtils.isNotEmpty(ids)) {
					//切割数据
					String[] split = ids.split(",");
					for (String id : split) {
						courierRepository.updateDelTagByIds(Long.parseLong(id));
					}
				}
	}

	

}
