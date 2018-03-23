package com.huying.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huying.bos.domain.take_delivery.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
