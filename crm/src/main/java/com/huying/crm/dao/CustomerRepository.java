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

		
		@Query("update Customer set type=1 where telephone = ?")
		void active(String telephone);

		//查看用户是否激活
		Customer findByTelephone(String telephone);

		//登录
		Customer findByTelephoneAndPassword(String telephone, String password);

		//根据地址查询定区ID
		@Query("select fixedAreaId from Customer where address=?")
		String findFixedAreaIdByAddress(String address);
		
}
