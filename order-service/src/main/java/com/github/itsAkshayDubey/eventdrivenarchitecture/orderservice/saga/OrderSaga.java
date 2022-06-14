package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.ReserveProductCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.ProductReservedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.core.events.OrderCreatedEvent;

@Saga
public class OrderSaga {
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

	@Autowired
	private transient CommandGateway cg;
	
	@SagaEventHandler(associationProperty = "orderId")
	@StartSaga
	public void handle(OrderCreatedEvent oce) {
		LOGGER.info("Inside Saga");
		ReserveProductCommand rpc = ReserveProductCommand.builder()
				.orderId(oce.getOrderId())
				.quantity(oce.getQuantity())
				.productId(oce.getProductId())
				.userId(oce.getUserId())
				.build();
		
		LOGGER.info("OrderCreatedEvent for orderId:"+rpc.getOrderId()+" and productId: "+rpc.getProductId());
		
		cg.send(rpc,new CommandCallback<ReserveProductCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ReserveProductCommand> arg0,
					CommandResultMessage<? extends Object> arg1) {
				if(arg1.isExceptional()) {
					
				}
				
			}
			
			
		});
	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservedEvent pre) {
		LOGGER.info("ProductReservedEvent for orderId:"+pre.getOrderId()+" and productId: "+pre.getProductId());
	}
}
