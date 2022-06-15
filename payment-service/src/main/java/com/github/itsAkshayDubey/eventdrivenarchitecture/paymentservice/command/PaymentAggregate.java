package com.github.itsAkshayDubey.eventdrivenarchitecture.paymentservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.ProcessPaymentCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.PaymentProcessedEvent;

@Aggregate
public class PaymentAggregate {

    private String orderId;
    
    @AggregateIdentifier
    private String paymentId;
    
    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand){

    	if(processPaymentCommand.getPaymentDetails() == null) {
    		throw new IllegalArgumentException("Missing payment details");
    	}
    	
    	if(processPaymentCommand.getOrderId() == null) {
    		throw new IllegalArgumentException("Missing orderId");
    	}
    	
    	if(processPaymentCommand.getPaymentId() == null) {
    		throw new IllegalArgumentException("Missing paymentId");
    	}
	
        AggregateLifecycle.apply(new PaymentProcessedEvent(processPaymentCommand.getOrderId(), 
                processPaymentCommand.getPaymentId()));
    }
	
	@EventSourcingHandler
	public void on(PaymentProcessedEvent ppe) {
		this.orderId = ppe.getOrderId();
		this.paymentId = ppe.getPaymentId();
	}
	
	
}
