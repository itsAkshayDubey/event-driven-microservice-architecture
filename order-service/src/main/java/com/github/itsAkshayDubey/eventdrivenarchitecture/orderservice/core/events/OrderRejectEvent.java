package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.events;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command.rest.OrderStatus;

import lombok.Value;

@Value
public class OrderRejectEvent {

	private final String orderId;
	private final String message;
	private OrderStatus orderStatus = OrderStatus.REJECTED;
}
