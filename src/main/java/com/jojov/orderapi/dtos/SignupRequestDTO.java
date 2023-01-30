package com.jojov.orderapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//DTO para solicitar la creacion del nuevo usuario

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignupRequestDTO {

	private String username;
	private String password;
	
	
	
	
}
