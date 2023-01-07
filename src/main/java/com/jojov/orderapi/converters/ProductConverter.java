package com.jojov.orderapi.converters;

import com.jojov.orderapi.dtos.ProductDTO;
import com.jojov.orderapi.entity.Product;

public class ProductConverter extends AbstractConverter<Product, ProductDTO> {

	@Override
	public ProductDTO fromEntity(Product entity) {
		// TODO Auto-generated method stub
		
		
		return  ProductDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.price(entity.getPrice())
				.build();
	}

	@Override
	public Product fromDTO(ProductDTO dto) {
		// TODO Auto-generated method stub
				return  Product.builder()
						.id(dto.getId())
						.name(dto.getName())
						.price(dto.getPrice())
						.build();
	}

}
