package com.jojov.orderapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jojov.orderapi.security.RestAuthenticationEntryPoint;
import com.jojov.orderapi.security.TokenAuthenticationFilter;

import jakarta.annotation.security.PermitAll;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

	@Bean
	public TokenAuthenticationFilter createTokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}
	
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public void configure(HttpSecurity http) throws Exception {
        http
        .cors() //permite que una aplicacion web pueda consumir una api de diferente dominio
        	.and()
        	.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
        .csrf()  // deshabilitando un mecanisco de seguridad
        	.disable()
        .formLogin() // se deshabilita un formulario
        	.disable()
        .httpBasic() // deshabilitando otro metodo de autenticacion
        	.disable()
        .exceptionHandling()
        	.authenticationEntryPoint(new RestAuthenticationEntryPoint())
        	.and()
        .authorizeHttpRequests()
        	.requestMatchers( // Que URL pueden ser accedidas sin un token
        		"/login",
        		 "/signup",
        		"/v2/api-docs"
        		)
        .permitAll()
        .anyRequest()
        .authenticated();
        
        http.addFilterBefore(createTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
