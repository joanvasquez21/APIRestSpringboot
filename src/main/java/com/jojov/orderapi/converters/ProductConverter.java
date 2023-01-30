package com.jojov.orderapi.converters;

import com.jojov.orderapi.dtos.ProductDTO;
import com.jojov.orderapi.entity.Product;

// Convertidor
public class ProductConverter extends AbstractConverter<Product, ProductDTO> {

	@Override
	public ProductDTO fromEntity(Product entity) {
		
		// Logica pra convertir un solo elemento
		
		return ProductDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.price(entity.getPrice())
				.build();
	}
	@Override
	public Product fromDTO(ProductDTO dto) {
		
		return Product.builder()
				.id(dto.getId())
				.name(dto.getName())
				.price(dto.getPrice())
				.build();
	}

	

}
