package com.github.itsAkshayDubey.eventdrivenarchitecture.products.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.entity.ProductLookupEntity;
import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.events.ProductCreatedEvent;
import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.repo.ProductLookupRepo;

@Component
@ProcessingGroup(value = "product-group")
public class ProductLookupEventsHandler {
	
	@Autowired
	private ProductLookupRepo repo;
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		
		ProductLookupEntity entity = new ProductLookupEntity(event.getProductId(), event.getTitle());
		
		repo.save(entity);
	}
	
	@ResetHandler
	public void reset() {
		repo.deleteAll();
	}

}
