package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.entity.Order;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.model.OrderSummary;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.repo.OrderRepo;

@Component
public class OrderQueryHandler {

	@Autowired
	private OrderRepo repo;
	
	@QueryHandler
	public OrderSummary handleQuery(FindOrderQuery foq) {
		Order order = repo.findByOrderId(foq.getOrderId());
		
		return new OrderSummary(order.getOrderId(), order.getOrderStatus(),"");
	}
	
}
