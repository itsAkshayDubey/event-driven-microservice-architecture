package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command.interceptors;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command.CreateProductCommand;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>>{

	private final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
	
	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> arg0) {
		
		return (index, command) -> {
			
			LOGGER.info("Command intercepted: "+command.getPayload());
			
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				
				CreateProductCommand cpc = (CreateProductCommand) command.getPayload();
				
				if(cpc.getPrice().compareTo(BigDecimal.ZERO)<=0)
					throw new IllegalArgumentException("Price cannot be less than zero");
				
				if(cpc.getTitle()==null || cpc.getTitle().isEmpty())
					throw new IllegalArgumentException("Title cannot be empty");
			}
			
			return command;
		};
	}

}
