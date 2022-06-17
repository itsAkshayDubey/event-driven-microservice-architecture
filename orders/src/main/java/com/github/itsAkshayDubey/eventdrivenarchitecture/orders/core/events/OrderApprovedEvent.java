package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.model.OrderStatus;

import lombok.Value;

@Value
public class OrderApprovedEvent {

	private final String orderId;
	private final  OrderStatus orderStatus = OrderStatus.APPROVED;
}
