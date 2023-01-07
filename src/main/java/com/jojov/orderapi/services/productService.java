package com.jojov.orderapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jojov.orderapi.entity.Product;
import com.jojov.orderapi.repository.ProductRepository;
import com.jojov.orderapi.validators.ProductValidator;

@Service
public class productService {

	@Autowired
	private ProductRepository productRepo;

	public Product findById(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("No existe el producto"));

		return product;
	}

	@Transactional
	public void delete(Long productId) {
		// Primero traemos el producto que queremos borrar, para saber si existe
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("No existe el producto"));
		// En caso de que exita, lo elimina
		productRepo.delete(product);
	}

	public List<Product> findAll() {
		List<Product> products = productRepo.findAll();
		return products;
	}
	
	public Product save(Product product) {
		
		
		ProductValidator.save(product);
		
		if(product.getId() == null) {
			Product newProduct = productRepo.save(product);
			return newProduct;
		}
		
		Product productUpdate = productRepo.findById(product.getId())
				.orElseThrow(() -> new RuntimeException("No existe el producto"));

		productUpdate.setName(product.getName());
		productUpdate.setPrice(product.getPrice());
		
		productRepo.save(productUpdate);
		
		return productUpdate;
		
	}
	



}
