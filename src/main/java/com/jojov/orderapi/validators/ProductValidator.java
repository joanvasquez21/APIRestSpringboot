package com.jojov.orderapi.validators;

import com.jojov.orderapi.entity.Product;
import com.jojov.orderapi.exceptions.ValidateServiceException;

public class ProductValidator {

	public static void save(Product product) {
		
		if(product.getName()==  null && product.getName().trim().isEmpty()) {
			throw new ValidateServiceException("El nombre es requerido");
		}
		if(product.getName().length() > 100) {
			throw new ValidateServiceException("El nombre es muy largo");
		}
		if(product.getPrice() == null) {
			throw new ValidateServiceException("El precio es requerido");
		}
		if(product.getPrice() < 0) {
			throw new ValidateServiceException("El precio es incorrecto");
		}
		
	}
}
