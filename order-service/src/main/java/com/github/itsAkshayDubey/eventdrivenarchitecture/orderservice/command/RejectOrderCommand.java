package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RejectOrderCommand {
	
	@TargetAggregateIdentifier
	private final String orderId;
	private final String message;
	
}
