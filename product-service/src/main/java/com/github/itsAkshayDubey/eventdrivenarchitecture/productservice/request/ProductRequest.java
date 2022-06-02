package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {

	private String title;
	private BigDecimal price;
	private Integer quantity;

}
