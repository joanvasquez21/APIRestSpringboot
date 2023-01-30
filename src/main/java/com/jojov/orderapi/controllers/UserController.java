package com.jojov.orderapi.controllers;

import java.sql.Wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jojov.orderapi.converters.UserConverter;
import com.jojov.orderapi.dtos.LoginRequestDTO;
import com.jojov.orderapi.dtos.LoginResponseDTO;
import com.jojov.orderapi.dtos.SignupRequestDTO;
import com.jojov.orderapi.dtos.UserDTO;
import com.jojov.orderapi.entity.User;
import com.jojov.orderapi.services.UserServices;
import com.jojov.orderapi.utils.WrapperResponse;


//Primer servicio para dar de alta un nuevo usuario
@RestController
public class UserController {

	@Autowired
	private UserServices userService;
	
	@Autowired
	private UserConverter userConverter;
	
	
	@PostMapping(value="/signup")
	public ResponseEntity<WrapperResponse<UserDTO>> signup(@RequestBody SignupRequestDTO request){
	
		User user = userService.createUser(userConverter.signup(request));
	
		return new WrapperResponse<>(true, "Success", userConverter.fromEntity(user)).createResponse();
	
	}
	// Generar metodo 
	@PostMapping(value="/login")
	public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request){
		
		LoginResponseDTO  response =  userService.login(request);
		return new WrapperResponse<>(true, "success", response).createResponse();
	}
}
