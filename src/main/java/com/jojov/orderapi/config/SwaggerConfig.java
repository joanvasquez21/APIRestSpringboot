package com.jojov.orderapi.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select() // que servicios especificaremos
				.apis(RequestHandlerSelectors.any()) // toma todas las clases y paquetes y autodocumentalo 
				.paths(PathSelectors.any()) //de todas las clases y paquetes te interesan, any(todo los packs)
				.build() // build para crear el docket
				.apiInfo(getApiInfo());
		}
	private ApiInfo getApiInfo() {
		return new ApiInfo(
			"Order Service Api",
			"Order service API description",
			"1.0",
			"http:joan",
			new Contact("joan","https://www.joan.com", "joan@gmail.com"),
			"LICENSE",
			"LICENSE URL",
			Collections.emptyList()
			);
		}
	}

}
