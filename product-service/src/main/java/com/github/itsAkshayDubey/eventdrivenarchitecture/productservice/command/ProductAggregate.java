package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.events.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

	public ProductAggregate() {}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand cpc) {
		
		if(cpc.getPrice().compareTo(BigDecimal.ZERO)<=0)
			throw new IllegalArgumentException("Price cannot be less than zero");
		
		if(cpc.getTitle()==null || cpc.getTitle().isEmpty())
			throw new IllegalArgumentException("Title cannot be empty");
		
		ProductCreatedEvent pce= new ProductCreatedEvent();
		
		BeanUtils.copyProperties(cpc, pce);
		
		AggregateLifecycle.apply(pce);
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent pce) {
		this.productId = pce.getProductId();
		this.title = pce.getTitle();
		this.price = pce.getPrice();
		this.quantity = pce.getQuantity();
	}
	
}
