package com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.itsAkshayDubey.eventdrivenarchitecture.productservice.core.entity.ProductLookupEntity;

@Repository
public interface ProductLookupRepo extends JpaRepository<ProductLookupEntity, String>{

	public ProductLookupEntity findByProductIdOrTitle(String productId, String title);
	
}
