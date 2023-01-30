package com.jojov.orderapi.validators;

import com.jojov.orderapi.entity.Order;
import com.jojov.orderapi.entity.OrderLine;
import com.jojov.orderapi.exceptions.ValidateServiceException;

public class OrderValidator {

	public static void save(Order order) {

		if (order.getTotal() == null) {
			throw new ValidateServiceException("El total es requerido");
		}
		if(order.getTotal() < 0 ) {
			throw new ValidateServiceException("El total es incorrecto");
		}
		if (order.getLines() == null || order.getLines().isEmpty()) {
			throw new ValidateServiceException("Las lineas son requeridas");
		}
		for (OrderLine line : order.getLines()) {
			if (line.getPrecio() == null) {
				throw new ValidateServiceException("El precio es requerido");
			}
			if (line.getPrecio() < 0) {
				throw new ValidateServiceException("El producto es requerido");
			}
			if(line.getProduct() == null || line.getProduct().getId() == null) {
				throw new ValidateServiceException("El producto es requerido"); 
			}
			if(line.getQuantity() == null) {
				throw new ValidateServiceException("La cantidad es requerida");
			}
			if(line.getQuantity() < 0) {
				throw new ValidateServiceException("La cantidad es incorrecta");
			}
			if(line.getTotal() == null) {
				throw new ValidateServiceException("El total es requerido");
			}
			if(line.getTotal() < 0) {
				throw new ValidateServiceException("La cantidad es incorrecta");
			}
			
			
		}

	}

}
