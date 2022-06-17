package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.query;

import lombok.Value;

@Value
public class FindOrderQuery {

	private final String orderId;
	
}
