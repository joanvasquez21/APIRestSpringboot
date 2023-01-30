package com.jojov.orderapi.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jojov.orderapi.converters.UserConverter;
import com.jojov.orderapi.dtos.LoginRequestDTO;
import com.jojov.orderapi.dtos.LoginResponseDTO;
import com.jojov.orderapi.entity.User;
import com.jojov.orderapi.exceptions.GeneralServiceException;
import com.jojov.orderapi.exceptions.NoDataFoundException;
import com.jojov.orderapi.exceptions.ValidateServiceException;
import com.jojov.orderapi.repository.UserRepository;
import com.jojov.orderapi.validators.UserValidators;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServices {

	@Value("${jwt.password}")
	private String jwtSecret;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	// Servicio para dar de alta a un usuario
	public User createUser(User user) {
		try {
			// Logida de negocio
			UserValidators.signup(user);

			User existUser = userRepo.findByUsername(user.getUsername()).orElse(null);
			if (existUser != null) {
				throw new ValidateServiceException("El nombre de usuario ya existe");
			}
			String encoder = passwordEncoder.encode(user.getPassword());
			user.setPassword(encoder) ;
			
			
			return userRepo.save(user);

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	public LoginResponseDTO login(LoginRequestDTO request) {
		try {
			// Hacer el login, buscar un usuario por su username
			User user = userRepo.findByUsername(request.getUsername())
					.orElseThrow(() -> new ValidateServiceException("Usuario o password incorrectos"));
			if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
				throw new ValidateServiceException("Usuario o password incorrectos");


			String token = createToken(user);

			// Necesitaremos el convertir para convertir la entidad usuario en DTO
			return new LoginResponseDTO(userConverter.fromEntity(user), token);

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	public String createToken(User user) {

		Date now = new Date();
		// Fecha en la que expire nuestro token
		Date expiryDate = new Date(now.getTime() + (1000 * 60 * 60));

		return Jwts.builder().setSubject(Long.toString(user.getId())).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret) // establecer el metodo de cifrado del token
				.compact(); // genera el token basado en los datos que acaban de enviar
	}

	// Servicio tipo boolean recibe como parametro el token
	//Servicio que nos permite validar el token
	public boolean validateToken(String token) {
		try {
			// validar
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (UnsupportedJwtException e) {
			log.error("Error");
		} catch (MalformedJwtException e) {
			log.error("Error");

		} catch (SignatureException e) {
			log.error("Error");

		} catch (ExpiredJwtException e) {
			log.error("Error");

		}

		return false;
	}
	
	public String getUsernameFromToken(String jwt) {
		try {
					Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();

		}catch(Exception e) {
			log.error(e.getMessage(),e);
			throw new ValidateServiceException("token invalido");
		}
		
		

	}

}
