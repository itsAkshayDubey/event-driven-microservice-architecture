package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.ReserveProductCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command.rest.OrderStatus;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.events.OrderCreatedEvent;

@Aggregate
public class OrderAggregate {
	@AggregateIdentifier
	public String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;

	public OrderAggregate(){}

	@CommandHandler
	public OrderAggregate(CreateOrderCommand coc) {
		if(coc.getQuantity()<=0)
			throw new IllegalArgumentException("Quantity cannot be less than zero");

		OrderCreatedEvent oce= new OrderCreatedEvent();

		BeanUtils.copyProperties(coc, oce);

		AggregateLifecycle.apply(oce);
	}
	
	@EventSourcingHandler
	public void on(OrderCreatedEvent oce) {
		
		this.orderId = oce.getOrderId();
		this.userId = oce.getUserId();
		this.productId = oce.getProductId();
		this.quantity = oce.getQuantity();
		this.addressId = oce.getAddressId();
		this.orderStatus = oce.getOrderStatus();

	}

}
