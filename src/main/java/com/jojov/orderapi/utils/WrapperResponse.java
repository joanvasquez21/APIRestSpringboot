package com.jojov.orderapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase que sirve como base para todas las respuestas, tipo generico de tipo T

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperResponse<T> {

	
	
	private boolean ok;
	
	private String message;
	 
	private T body;
	
	public ResponseEntity<WrapperResponse<T>> createResponse(HttpStatus status){
		return new ResponseEntity<WrapperResponse<T>>(this,status);
	}
	
	public ResponseEntity<WrapperResponse<T>> createResponse(){
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
