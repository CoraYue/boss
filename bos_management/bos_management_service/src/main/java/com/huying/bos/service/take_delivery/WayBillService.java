package com.huying.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.huying.bos.domain.base.SubArea;
import com.huying.bos.domain.take_delivery.WayBill;

public interface WayBillService {

	void save(WayBill wayBill);

	Page<WayBill> pageQuery(Pageable pageable);

	
}
