package com.huying.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huying.crm.dao.CustomerRepository;
import com.huying.crm.domain.Customer;
import com.huying.crm.service.CustomerService;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findAll() {
		
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findCustomersUnAssociated() {
		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findCustomersAssociated(String fixedAreaId) {
		return customerRepository.findByFixedAreaId(fixedAreaId);
	}
}
