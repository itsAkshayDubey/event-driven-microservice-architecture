package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.query.rest;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.query.FindProductsQuery;

@RestController
@RequestMapping("/api")
public class ProductQueryController {

	@Autowired
	QueryGateway qg;
	
	@GetMapping("/products")
	public List<ProductRestModel> getProducts(){
		FindProductsQuery fpq = new FindProductsQuery();
		List<ProductRestModel> list =  qg.query(fpq, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
	
		return list;
	}
	
}
