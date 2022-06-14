package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.entity.Product;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.events.ProductCreatedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.repo.ProductRepository;

@Component
@ProcessingGroup(value = "product-group")
public class ProductProjection {
	
	@Autowired
	ProductRepository repo;
	
	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException ex) {
		
	}
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception ex) throws Exception{
		
		throw ex;
		
	}

	@EventHandler
	public void on(ProductCreatedEvent pce) {
		Product product = new Product();
		BeanUtils.copyProperties(pce, product);
		
		repo.save(product);
		
	}
	
}
