package com.jojov.orderapi.dtos;

import com.jojov.orderapi.entity.Order;
import com.jojov.orderapi.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class OrderLineDTO {
	private Long id;

	private Order order;

	private ProductDTO product;

	private Double precio;

	private Double quantity;

	private Double total;
}
