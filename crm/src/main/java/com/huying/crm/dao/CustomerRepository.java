package com.huying.crm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huying.crm.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
