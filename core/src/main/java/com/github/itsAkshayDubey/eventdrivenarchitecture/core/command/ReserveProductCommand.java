package com.github.itsAkshayDubey.eventdrivenarchitecture.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveProductCommand {

	@TargetAggregateIdentifier
	private final String productId;
	private final int quantity;
	private final String orderId;
	private final String userId;
	
}
