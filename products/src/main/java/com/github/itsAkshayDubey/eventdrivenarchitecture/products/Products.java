package com.github.itsAkshayDubey.eventdrivenarchitecture.products;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.github.itsAkshayDubey.eventdrivenarchitecture.products.command.interceptors.CreateProductCommandInterceptor;
import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.errorhandling.ProductServiceEventsErrorHandler;

@SpringBootApplication
@EnableEurekaClient
public class Products {

	public static void main(String[] args) {
		SpringApplication.run(Products.class, args);
	}
	
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer epc) {
	
		epc.registerListenerInvocationErrorHandler("product-group", conf -> new ProductServiceEventsErrorHandler());
	}
	
	@Bean(name = "productSnapshotTriggerDefinition")
	public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 3);
	}
	
}
