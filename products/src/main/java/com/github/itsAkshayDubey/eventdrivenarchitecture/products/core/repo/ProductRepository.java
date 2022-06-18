package com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.itsAkshayDubey.eventdrivenarchitecture.products.core.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

	Product findByProductId(String productId);
}
