package com.huying.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Repository;

import com.huying.crm.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	//查找未关联定区的客户
	List<Customer> findByFixedAreaIdIsNull();

	//查找yi关联定区的客户
		List<Customer> findByFixedAreaId(String fixedAreaId);

		//解绑客户
		@Modifying
		@Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
		void unbindByFixedAreaId(String fixedAreaId);

		
		@Modifying
		@Query("update Customer set fixedAreaId = ? where id = ?")
		void bindFixedAreaById(String fixedAreaId, Long customerId);

		@Modifying
		@Query("update Customer set fixedAreaId = null where id = ?")
		void unBindFixedAreaById(Long unCustomerId);
}
