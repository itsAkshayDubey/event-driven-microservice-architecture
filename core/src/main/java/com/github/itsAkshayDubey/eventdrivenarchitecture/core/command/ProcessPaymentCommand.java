package com.github.itsAkshayDubey.eventdrivenarchitecture.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.payment.PaymentDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ProcessPaymentCommand {

	@TargetAggregateIdentifier
	private final String paymentId;
	private final String orderId;
	private final PaymentDetails paymentDetails;
	
}
