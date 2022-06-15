package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.events;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command.rest.OrderStatus;

import lombok.Value;

@Value
public class OrderApprovedEvent {

	private final String orderId;
	private final  OrderStatus orderStatus = OrderStatus.APPROVED;
}
