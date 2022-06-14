package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command.interceptors;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command.CreateProductCommand;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.entity.ProductLookupEntity;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.repo.ProductLookupRepo;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>>{

	@Autowired
	private ProductLookupRepo repo;
	private final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
	
	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> arg0) {
		
		return (index, command) -> {
			
			LOGGER.info("Command intercepted: "+command.getPayload());
			
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				
				CreateProductCommand cpc = (CreateProductCommand) command.getPayload();
				
				ProductLookupEntity entity =  repo.findByProductIdOrTitle(cpc.getProductId(), cpc.getTitle());
				
				if(entity!=null)
					throw new IllegalStateException(String.format("Product exists", cpc.getProductId(),cpc.getTitle()));
				
			}
			
			return command;
		};
	}

}
