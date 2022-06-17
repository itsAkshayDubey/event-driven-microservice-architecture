package com.github.itsAkshayDubey.eventdrivenarchitecture.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Edge {

	public static void main(String[] args) {
		SpringApplication.run(Edge.class, args);
	}

}
