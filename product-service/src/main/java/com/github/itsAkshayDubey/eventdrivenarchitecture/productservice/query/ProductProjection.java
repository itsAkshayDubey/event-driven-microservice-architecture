package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.ProductReservationCancelledEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.ProductReservedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.entity.Product;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.events.ProductCreatedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.repo.ProductRepository;

@Component
@ProcessingGroup(value = "product-group")
public class ProductProjection {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductProjection.class);
	
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
	
	@EventHandler
	public void on(ProductReservedEvent pre) {
		LOGGER.info("ProductReservedEvent for orderId:"+pre.getOrderId()+" and productId: "+pre.getProductId());
		Product product = repo.findByProductId(pre.getProductId());
		product.setQuantity(product.getQuantity() - pre.getQuantity());
		repo.save(product);
	}
	
	@EventHandler
	public void on(ProductReservationCancelledEvent prce) {
		Product product = repo.findByProductId(prce.getProductId());
		product.setQuantity(product.getQuantity() + prce.getQuantity());
		repo.save(product);
	}
	
}
