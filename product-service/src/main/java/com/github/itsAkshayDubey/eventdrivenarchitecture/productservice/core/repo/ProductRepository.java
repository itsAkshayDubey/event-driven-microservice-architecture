package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

	Product findByProductId(String productId);
}
