package com.huying.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.bos.dao.take_delivery.PromotionRepository;
import com.huying.bos.domain.take_delivery.Promotion;
import com.huying.bos.service.take_delivery.PromotionService;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService{

	@Autowired
	private PromotionRepository promotionRepository;
	
	@Override
	public void save(Promotion promotion) {
		promotionRepository.save(promotion);
	}

}
