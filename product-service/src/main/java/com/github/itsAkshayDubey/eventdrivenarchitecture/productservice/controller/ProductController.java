package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class ProductController {

	@GetMapping("/product")
	public String getProduct() {
		return "GET mapping";
	}
	
}
