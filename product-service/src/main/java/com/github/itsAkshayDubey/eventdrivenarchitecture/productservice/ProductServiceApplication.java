package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.command.interceptors.CreateProductCommandInterceptor;
import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.errorhandling.ProductServiceEventsErrorHandler;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	
	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer epc) {
	
		epc.registerListenerInvocationErrorHandler("product-group", conf -> new ProductServiceEventsErrorHandler());
	}
	
}
