package com.jojov.orderapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jojov.orderapi.converters.ProductConverter;
import com.jojov.orderapi.dtos.ProductDTO;
import com.jojov.orderapi.entity.Product;
import com.jojov.orderapi.repository.ProductRepository;
import com.jojov.orderapi.services.productService;
import com.jojov.orderapi.utils.WrapperResponse;

@RestController
public class ProductController {

	@Autowired
	productService productService;

	private ProductConverter converter = new ProductConverter();

	private List<Product> products = new ArrayList<>();

	// Para hacer un CRUD de un producto, necesitamos crear 4 metodos:
	// 1. Permita consultar todos los productos,3. crearlo 2. Consultar producto por
	// id, 3. actualizar .4. eliminar

	// permite consultar por un solo producto
	@GetMapping(value = "/products/{productId}")
	public ResponseEntity<WrapperResponse<ProductDTO>> findById(@PathVariable("productId") Long productId) {
		// Servicio
		Product product = productService.findById(productId);
		// Convierte de Entidad a ProductDTO
		ProductDTO productDTO = converter.fromEntity(product);
		// Ahora responderas con un wrapper response
		return new WrapperResponse<>(true, "success", productDTO).createResponse(HttpStatus.OK);
	}

	@DeleteMapping(value = "/products/{productId}")
	public ResponseEntity<?> delete(@PathVariable("productId") Long productId) {
		productService.delete(productId);
		return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);

	}

	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> findAll(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);

		List<Product> products = productService.findAll(page);
		List<ProductDTO> productDTO = converter.fromEntity(products);
		return new WrapperResponse(true, "success", productDTO).createResponse(HttpStatus.OK);
	}

	// Creacion
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
		Product newProduct = productService.save(converter.fromDTO(product));

		ProductDTO productDTO = converter.fromEntity(newProduct);
		return new WrapperResponse(true, "success", productDTO).createResponse(HttpStatus.CREATED);
	}

	@PutMapping(value = "/products")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product) {
		Product productUpdate = productService.save(converter.fromDTO(product));

		ProductDTO productDTO = converter.fromEntity(productUpdate);

		return new WrapperResponse(true, "success", productDTO).createResponse(HttpStatus.OK);
	}

}
