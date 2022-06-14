package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.events;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command.rest.OrderStatus;

import lombok.Data;

@Data
public class OrderCreatedEvent {
	private String orderId;
	private String productId;
	private String userId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;
}
