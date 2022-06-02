package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {
	
	private String title;
	private BigDecimal price;
	private Integer quantity;

}
