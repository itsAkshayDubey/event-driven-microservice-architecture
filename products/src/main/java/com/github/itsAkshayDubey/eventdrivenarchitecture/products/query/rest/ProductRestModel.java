package com.github.itsAkshayDubey.eventdrivenarchitecture.products.query.rest;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRestModel {

	private String title;
	private BigDecimal price;
	private Integer quantity;
	
}
