package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.entity.Product;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.events.ProductCreatedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.repo.ProductRepository;

@Component
public class ProductProjection {
	
	@Autowired
	ProductRepository repo;

	@EventHandler
	public void on(ProductCreatedEvent pce) {
		Product product = new Product();
		BeanUtils.copyProperties(pce, product);
		
		repo.save(product);
		
	}
	
}
