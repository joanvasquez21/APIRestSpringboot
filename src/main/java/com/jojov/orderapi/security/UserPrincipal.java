package com.jojov.orderapi.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.jojov.orderapi.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

//validarlo del lado del servidor y injectar el usuario para poder luego recuperar 

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

	//
	private User user; 
	//Coleccionde autorizaciones
	private Collection<? extends GrantedAuthority> authorities; 
	//generar metodo estatico que nos permita construir el objeto de forma mas sencilla
	public static UserPrincipal create(User user) {
		
		List<GrantedAuthority> authorities = Collections
				.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	//Generar 
		return new UserPrincipal(user, authorities);
	}
	//coleccion de autorizaciones
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
	//definir o implementar los metodos que nos obliga a implementar la interfaz
	
		
		return authorities;
	}

	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public static User getCurrentUser() {
		// Para saber el usuario con el cual se crea la orden
		
		//Regresa un userPrincipal
		 UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Para obtener el usuario
		 return  principal.getUser();
		
	}
	
}
