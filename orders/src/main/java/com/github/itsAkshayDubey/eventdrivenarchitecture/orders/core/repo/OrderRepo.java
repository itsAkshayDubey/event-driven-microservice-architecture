package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.entity.Order;

public interface OrderRepo extends JpaRepository<Order, String>{
	
	Order findByOrderId(String orderId);

}
