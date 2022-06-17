package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.saga;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.CancelProductReservationCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.ProcessPaymentCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.ReserveProductCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.PaymentProcessedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.ProductReservationCancelledEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.ProductReservedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.query.FetchUserPaymentDetailsQuery;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.user.User;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.command.ApproveOrderCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.command.RejectOrderCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events.OrderApprovedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events.OrderCreatedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.events.OrderRejectEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.model.OrderSummary;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.query.FindOrderQuery;

@Saga
public class OrderSaga {
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

	@Autowired
	private transient CommandGateway cg;
	
	@Autowired
	private transient QueryGateway qg;
	
	@Autowired
	private transient QueryUpdateEmitter que;
	
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
					
					RejectOrderCommand roc = new RejectOrderCommand(oce.getOrderId(), arg1.exceptionResult().getMessage());
					cg.send(roc);
					
				}
				
			}
			
			
		});
	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservedEvent pre) {
		LOGGER.info("ProductReservedEvent for orderId:"+pre.getOrderId()+" and productId: "+pre.getProductId());
		
		FetchUserPaymentDetailsQuery fupdq = new FetchUserPaymentDetailsQuery(pre.getUserId());
		
		
		User user = null;
		try {
		user = qg.query(fupdq, ResponseTypes.instanceOf(User.class)).join();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.cancelProductReservation(pre,e.getMessage());
			return;
		}
		
		if(user == null) {
			this.cancelProductReservation(pre,"Could not fetch user payment details.");
			return;
		}
		
		LOGGER.info("Successfully fetched payment details for user: "+user.getFirstName()+" "+user.getLastName()+".");
	
		ProcessPaymentCommand ppc = ProcessPaymentCommand.builder()
				.orderId(pre.getOrderId())
				.paymentDetails(user.getPaymentDetails())
				.paymentId(UUID.randomUUID().toString())
				.build();
		
		String result = null;
		try {
			result = cg.sendAndWait(ppc, 10, TimeUnit.SECONDS);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			this.cancelProductReservation(pre,e.getMessage());
		}
		
		if(result==null) {
			LOGGER.info("The ProcessPaymentCommand is null. Initialting compensating transaction");
			this.cancelProductReservation(pre,"Could not process payment details with provided payment details.");
			return;
		}
	}
	
	private void cancelProductReservation(ProductReservedEvent pre, String reason) {
		CancelProductReservationCommand cprc = CancelProductReservationCommand.builder()
				.orderId(pre.getOrderId())
				.productId(pre.getProductId())
				.quantity(pre.getQuantity())
				.userId(pre.getUserId())
				.message(reason)
				.build();
		cg.send(cprc);
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(PaymentProcessedEvent ppe) {
		
		ApproveOrderCommand aoc = new ApproveOrderCommand(ppe.getOrderId());
		cg.send(aoc);
		
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	public void handle(OrderApprovedEvent oae) {
		LOGGER.info("Order approved for order id:"+oae.getOrderId());
		
		que.emit(FindOrderQuery.class, query -> true, new OrderSummary(oae.getOrderId(), oae.getOrderStatus(),""));
		
		
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservationCancelledEvent prce) {
		RejectOrderCommand roc = new RejectOrderCommand(prce.getOrderId(), prce.getMessage());
		cg.send(roc);
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	@EndSaga
	public void handle(OrderRejectEvent ore) {
		LOGGER.info("Order rejected for order id: "+ore.getOrderId());
		que.emit(FindOrderQuery.class, query -> true, new OrderSummary(ore.getOrderId(), ore.getOrderStatus(), ore.getMessage()));
	}
}

