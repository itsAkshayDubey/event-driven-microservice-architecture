package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.entity.Order;

public interface OrderRepo extends JpaRepository<Order, String>{

}
