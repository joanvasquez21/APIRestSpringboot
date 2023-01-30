package com.jojov.orderapi.validators;

import org.apache.catalina.User;

import com.jojov.orderapi.exceptions.ValidateServiceException;

public class UserValidators {

	public static void signup(com.jojov.orderapi.entity.User user) {
		
		if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
			throw new ValidateServiceException("El nombre de usuario es requerido");
		}
		if(user.getUsername().length() > 30) {
			throw new ValidateServiceException("La longitud no tiene que ser mas de 30");
		}
		if(user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new ValidateServiceException("El password es  required");
		}
		
	}
}
