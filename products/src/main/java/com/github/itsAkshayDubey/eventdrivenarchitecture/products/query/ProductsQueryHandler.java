package com.github.itsAkshayDubey.eventdrivenarchitecture.products.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.entity.Product;
import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.repo.ProductRepository;
import com.github.itsAkshayDubey.eventdrivenarchitecture.products.query.rest.ProductRestModel;

@Component
public class ProductsQueryHandler {

	@Autowired
	ProductRepository repo;
	
	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductsQuery fpq){
		List<ProductRestModel> list = new ArrayList<>();
		
		List<Product> storedProducts = repo.findAll();
		
		for(Product product: storedProducts) {
			ProductRestModel prm = new ProductRestModel();
			BeanUtils.copyProperties(product, prm);
			list.add(prm);
		}
		
		return list;
	}
	
}
