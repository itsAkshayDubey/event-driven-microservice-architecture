package com.github.itsAkshayDubey.eventdrivenarchitecture.products.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.itsAkshayDubey.eventdrivenarchitecture.products.command.CreateProductCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.entity.Product;

@RestController
@RequestMapping(path = "/api")
public class ProductCommandController {

	@Autowired
	Environment env;
	
	@Autowired
	CommandGateway cg;
	
	@PostMapping("/product")
	public String getProduct(@RequestBody ProductRestModel product) {
		
		CreateProductCommand cpc = CreateProductCommand.builder().price(product.getPrice())
				.title(product.getTitle())
				.quantity(product.getQuantity())
				.productId(UUID.randomUUID().toString())
				.build();
		String returnValue = cg.sendAndWait(cpc);
		return returnValue;
	}
	
}
