package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.query;

import lombok.Value;

@Value
public class FindOrderQuery {

	private final String orderId;
	
}
