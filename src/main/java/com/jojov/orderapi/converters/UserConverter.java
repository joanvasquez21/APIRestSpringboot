package com.jojov.orderapi.converters;

import com.jojov.orderapi.dtos.SignupRequestDTO;
import com.jojov.orderapi.dtos.UserDTO;
import com.jojov.orderapi.entity.User;

public class UserConverter  extends AbstractConverter<User, UserDTO>{

	@Override
	public UserDTO fromEntity(User entity) {
		// TODO Auto-generated method stub
		// construir un userdto a apartir de una entidad
		if(entity == null) return null;
		return UserDTO.builder()
				.id(entity.getId())
				.username(entity.getUsername())
				.build();
	}

	@Override
	public User fromDTO(UserDTO dto) {
		// TODO Auto-generated method stub
		if(dto == null) return null;
		return User.builder()
				.id(dto.getId())
				.username(dto.getUsername())
				.build();
	}

	
	public User signup(SignupRequestDTO dto) {
		if(dto == null) return null;

		return User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
	}
}
