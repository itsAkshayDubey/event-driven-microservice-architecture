package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.CancelProductReservationCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.command.ReserveProductCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.ProductReservationCancelledEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.core.events.ProductReservedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.events.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductAggregate.class);
	
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
	
	@CommandHandler
	public void handle(ReserveProductCommand rpc) {
		
		LOGGER.info("Inside handle RPC");
		
		if(quantity<rpc.getQuantity()) {
			throw new IllegalArgumentException("Less items in stock. :(");
		}
		
		ProductReservedEvent pre = ProductReservedEvent.builder()
				.orderId(rpc.getOrderId())
				.quantity(rpc.getQuantity())
				.productId(rpc.getProductId())
				.userId(rpc.getUserId())
				.build();
		
		AggregateLifecycle.apply(pre);
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent pce) {
		this.productId = pce.getProductId();
		this.title = pce.getTitle();
		this.price = pce.getPrice();
		this.quantity = pce.getQuantity();
	}
	
	@EventSourcingHandler
	public void on(ProductReservedEvent pre) {
		this.quantity -= pre.getQuantity();
	}
	
	@CommandHandler
	public void handle(CancelProductReservationCommand cprc) {
		ProductReservationCancelledEvent prce = ProductReservationCancelledEvent.builder()
				.orderId(cprc.getOrderId())
				.productId(cprc.getProductId())
				.quantity(cprc.getQuantity())
				.userId(cprc.getUserId())
				.message(cprc.getMessage())
				.build();
		
		AggregateLifecycle.apply(prce);
	}
	
	@EventSourcingHandler
	public void on(ProductReservationCancelledEvent prce) {
		this.quantity += prce.getQuantity();
	}
	
}
