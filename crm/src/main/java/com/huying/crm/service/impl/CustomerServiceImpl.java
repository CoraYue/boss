package com.huying.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	@Override
	public void assignCustomers2FixedArea(String fixedAreaId , Long[] customerIds) {
		//根据定区id把客户解绑
		if(StringUtils.isNotEmpty(fixedAreaId)) {
			customerRepository.unbindByFixedAreaId(fixedAreaId);
		}
		//把需要关联的绑定
		if(customerIds!=null && fixedAreaId.length()>0) {
			for (Long customerId : customerIds) {
				customerRepository.bindFixedAreaById(fixedAreaId, customerId);
			}
		}
	}
	
	@Override
	public void assignCustomers2FixedArea2(Long[] uncustomerIds) {
		//把解绑的用户再次解绑
			for (Long unCustomerId : uncustomerIds) {
				customerRepository.unBindFixedAreaById(unCustomerId);
			}
	}

	//注册用户
	@Override
	public void save(Customer customer) {
		customerRepository.save(customer);		
	}

	//激活用户
	@Override
	public void active(String telephone) {
		customerRepository.active(telephone);	
	}

	//校验用户是否激活
	@Override
	public Customer isActived(String telephone) {
		return customerRepository.findByTelephone(telephone);
	}

	//登录
	@Override
	public Customer login(String telephone, String password) {
		return customerRepository.findByTelephoneAndPassword(telephone,password);
	}

	//校验手机号
	@Override
	public Customer findByTelephone(String telephone) {
		
		return customerRepository.findByTelephone(telephone);
	}
}
