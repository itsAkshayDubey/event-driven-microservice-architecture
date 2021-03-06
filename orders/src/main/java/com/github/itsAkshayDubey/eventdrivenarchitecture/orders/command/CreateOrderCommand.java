package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.model.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
	
	
	@TargetAggregateIdentifier
    public final String orderId;
    private final String userId;
    private final String productId;
    private final int quantity;
    private final String addressId;
    private final OrderStatus orderStatus;

}
