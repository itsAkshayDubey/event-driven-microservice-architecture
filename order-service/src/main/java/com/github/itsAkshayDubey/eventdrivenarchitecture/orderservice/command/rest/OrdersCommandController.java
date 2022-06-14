package com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.itsAkshayDubey.eventdrivenarchitecture.orderservice.command.CreateOrderCommand;

@RestController
@RequestMapping("/api")
public class OrdersCommandController {

	@Autowired
	Environment env;
	
	@Autowired
	CommandGateway cg;
	
	@PostMapping("/orders")
	public String getProduct(@RequestBody OrderRestModel order) {
		
		CreateOrderCommand coc = CreateOrderCommand.builder()
				.productId(order.getProductId())
				.quantity(order.getQuantity())
				.addressId(order.getAddressId())
				.orderId(UUID.randomUUID().toString())
				.userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
				.orderStatus(OrderStatus.CREATED)
				.build();
		String returnValue = cg.sendAndWait(coc);
		return returnValue;
	}
	
	
}
