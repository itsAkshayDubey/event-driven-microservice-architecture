package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command.rest;

import lombok.Data;

@Data
public class OrderRestModel {
	
    public final String orderId;
    private final String userId;
    private final String productId;
    private final int quantity;
    private final String addressId;
    private final OrderStatus orderStatus;

}
