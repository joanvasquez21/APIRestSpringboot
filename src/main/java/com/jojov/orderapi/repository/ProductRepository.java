package com.jojov.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jojov.orderapi.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	
	
}
