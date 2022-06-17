package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.entity.Order;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events.OrderApprovedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events.OrderCreatedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events.OrderRejectEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.repo.OrderRepo;

@Component
@ProcessingGroup("order-group")
public class OrderProjection {
	
	@Autowired
	private OrderRepo repo;
	
	@EventHandler
	void on(OrderCreatedEvent oce) {
		Order order = new Order();
		BeanUtils.copyProperties(oce, order);
		
		repo.save(order);
		
	}
	
	@EventHandler
	void on(OrderApprovedEvent oae) {
		Order order = repo.findByOrderId(oae.getOrderId());
		
		if(order == null)
			return;
		
		order.setOrderStatus(oae.getOrderStatus());
		repo.save(order);
				
		
	}
	
	@EventHandler
	void on(OrderRejectEvent ore) {
		Order order = repo.findByOrderId(ore.getOrderId());
		
		if(order == null)
			return;
		
		order.setOrderStatus(ore.getOrderStatus());
		repo.save(order);
	}
	

}
