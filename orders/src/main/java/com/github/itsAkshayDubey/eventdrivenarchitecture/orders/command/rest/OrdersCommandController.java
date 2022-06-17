package com.github.itsAkshayDubey.eventdrivenarchitecture.orders.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.command.CreateOrderCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.model.OrderStatus;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.core.model.OrderSummary;
import com.github.itsAkshayDubey.eventdrivenarchitecture.orders.query.FindOrderQuery;

import io.axoniq.axonserver.connector.query.SubscriptionQueryResult;
import io.axoniq.axonserver.grpc.query.SubscriptionQueryResponse;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class OrdersCommandController {

	@Autowired
	CommandGateway cg;

	@Autowired
	QueryGateway qg;

	@PostMapping("/orders")
	public OrderSummary getProduct(@RequestBody OrderRestModel order) {

		String orderId = UUID.randomUUID().toString();

		CreateOrderCommand coc = CreateOrderCommand.builder()
				.productId(order.getProductId())
				.quantity(order.getQuantity())
				.addressId(order.getAddressId())
				.orderId(orderId)
				.userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
				.orderStatus(OrderStatus.CREATED)
				.build();

		org.axonframework.queryhandling.SubscriptionQueryResult<OrderSummary, OrderSummary> resp = qg.subscriptionQuery(new FindOrderQuery(orderId), ResponseTypes.instanceOf(OrderSummary.class), ResponseTypes.instanceOf(OrderSummary.class));

		try {
			cg.sendAndWait(coc);

			return resp.updates().blockFirst();

		}
		finally {
			resp.close();
		}
	}


}
