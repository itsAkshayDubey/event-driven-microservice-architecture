package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.events.OrderCreatedEvent;

@Saga
public class OrderSaga {

	@Autowired
	private transient CommandGateway cg;
	
	@SagaEventHandler(associationProperty = "orderId")
	@StartSaga
	public void handle(OrderCreatedEvent oce) {
		
	}
	
}
