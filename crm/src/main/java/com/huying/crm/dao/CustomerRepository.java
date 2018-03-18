package com.huying.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Repository;

import com.huying.crm.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	//查找未关联定区的客户
	List<Customer> findByFixedAreaIdIsNull();

	//查找yi关联定区的客户
		List<Customer> findByFixedAreaId(String fixedAreaId);
}
