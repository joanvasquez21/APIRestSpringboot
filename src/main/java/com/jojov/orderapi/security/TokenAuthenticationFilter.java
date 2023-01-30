package com.jojov.orderapi.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.userdetails.ReactiveUserDetailsServiceResourceFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jojov.orderapi.entity.User;
import com.jojov.orderapi.exceptions.NoDataFoundException;
import com.jojov.orderapi.repository.UserRepository;
import com.jojov.orderapi.services.UserServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//onceper se ejecuta para cada peticion que llegue a spring se va a ejecutar el filtro
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	// El primer paso es obtener el token

	@Autowired
	private UserServices userService;

	@Autowired
	private UserRepository userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		try {
				String jwt = getJwtFromRequest(request);
		if(StringUtils.hasText(jwt) && userService.validateToken(jwt) ) {
			
			//Extraemos el usuario
			String userName = userService.getUsernameFromToken(jwt);
			//Obtenemos el usuario
			User user = userRepo.findByUsername(userName)
					.orElseThrow(() -> new NoDataFoundException("No existe el usuario"));
		
			
			UserPrincipal principal = UserPrincipal.create(user);
			// Generar una clase que encapsula 
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
			// enviar la web authentication, mediante los servlet estamos establenciendo la seguridad
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// Con esta clase podemos extraer el usuario autenticado desde cualquier parte de la aplicacion
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			 
		}
		}catch(Exception e) {
			log.error("Error al autenticar al usuario", e);
		}
		
		filterChain.doFilter(request, response);
	
	}

	public String getJwtFromRequest(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;

	}

}
