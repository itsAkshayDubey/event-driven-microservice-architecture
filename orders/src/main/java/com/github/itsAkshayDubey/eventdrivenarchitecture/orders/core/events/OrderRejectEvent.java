package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.model.OrderStatus;

import lombok.Value;

@Value
public class OrderRejectEvent {

	private final String orderId;
	private final String message;
	private OrderStatus orderStatus = OrderStatus.REJECTED;
}
