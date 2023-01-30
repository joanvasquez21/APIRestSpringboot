package com.jojov.orderapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

	// nos responde el usuario que se acaba de logear
	private UserDTO user;
	// regresa una variabe token
	private String token;
	
	
}
