package com.github.itsAkshayDubey.eventdrivenarchitecture.core.events;

import lombok.Builder;
import lombok.Data;

@Data
public class PaymentProcessedEvent {

    private final String orderId;
    private final String paymentId;
	
}
