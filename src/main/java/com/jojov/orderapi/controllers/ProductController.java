package com.jojov.orderapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jojov.orderapi.converters.ProductConverter;
import com.jojov.orderapi.dtos.ProductDTO;
import com.jojov.orderapi.entity.Product;
import com.jojov.orderapi.repository.ProductRepository;
import com.jojov.orderapi.services.productService;

@RestController
public class ProductController {

	@Autowired
	productService productService;
	
	private ProductConverter converter;
	

	private List<Product> products = new ArrayList<>();

	// Para hacer un CRUD de un producto, necesitamos crear 4 metodos:
	// 1. Permita consultar todos los productos,3. crearlo 2. Consultar producto por
	// id, 3. actualizar .4. eliminar

	// permite consultar por un solo producto
	@GetMapping(value = "/products/{productId}")
	public ResponseEntity<ProductDTO> findById(@PathVariable("productId") Long productId) {
		Product product = productService.findById(productId);
		ProductDTO productDTO = converter.fromEntity(product);
				
				
		return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/products/{productId}")
	public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
		productService.delete(productId);
		return new ResponseEntity(HttpStatus.OK);

	}

	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> findAll() {
		List<Product> products = productService.findAll();
		
		List<ProductDTO>  productDTO =  products.stream().map(product -> {
		return 	
			ProductDTO.builder()
			.id(product.getId())
			.name(product.getName())
			.price(product.getPrice())
			.build();
		})
		.collect(Collectors.toList());
		

		return new ResponseEntity<List<ProductDTO>>(productDTO, HttpStatus.OK);
	}

	// Creacion
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
		Product newProduct = productService.save(product);

		ProductDTO  productDTO =  ProductDTO.builder()
		.id(product.getId())
		.name(product.getName())
		.price(product.getPrice())
		.build();
		
		return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/products")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product) {
		Product productUpdate = productService.save(product);
		
		ProductDTO  productDTO =  ProductDTO.builder()
				.id(product.getId())
				.name(product.getName())
				.price(product.getPrice())
				.build();
		
		
		return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
	}

}
