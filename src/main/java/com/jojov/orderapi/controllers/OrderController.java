package com.jojov.orderapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jojov.orderapi.converters.OrderConverter;
import com.jojov.orderapi.dtos.OrderDTO;
import com.jojov.orderapi.entity.Order;
import com.jojov.orderapi.services.OrderService;
import com.jojov.orderapi.utils.WrapperResponse;

import ch.qos.logback.core.pattern.Converter;

@RestController
public class OrderController {

	// Convertirlos con el convertidor
	OrderConverter converter = new OrderConverter();

	@Autowired
	private OrderService orderService;
	
	
	// Metodo para buscar todos los productos
	@GetMapping(value="/orders")
	public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(@RequestParam(name="pageNumber", required=false, defaultValue = "0" ) int pageNumber,
								  @RequestParam(name="pageSize", required=false, defaultValue="0") int pageSize){
		
		// Con esto tenemos el objeto pageable
		Pageable page = PageRequest.of(pageNumber, pageSize);
		//Hacer una lista
		List<Order> orders = orderService.findAll(page); // orderService.findAll
		
		return new WrapperResponse(true,"success", converter.fromEntity(orders)).createResponse();
		
	}

	// Metodos de buscas unitarias, GetMapping porque estamos haciendo una consulta

	@GetMapping(value = "/orders/{id}")
	public ResponseEntity<WrapperResponse<OrderDTO>> findById(@PathVariable(name = "id") Long id) {
		// Obtner la orden
		Order order = orderService.findById(id); // orderservice.findbyid(id)

		return new WrapperResponse(true, "success", converter.fromEntity(order)).createResponse();
	}

	// Crear una nueva orden
	@PostMapping(value = "/orders")
	public ResponseEntity<WrapperResponse<OrderDTO>> create(@RequestBody OrderDTO order) {
		// Hay que mandarlo al servicio para la creacion
		Order newOrder = orderService.save(converter.fromDTO(order));

		return new WrapperResponse(true, "success", converter.fromEntity(newOrder)).createResponse();
	}

	// Crear una nueva orden
	@PutMapping(value = "/orders")
	public ResponseEntity<WrapperResponse<OrderDTO>> update(@RequestBody OrderDTO order) {
		// Hay que mandarlo al servicio para la creacion
		Order newOrder = orderService.save(converter.fromDTO(order)); //

		return new WrapperResponse(true, "success", converter.fromEntity(newOrder)).createResponse();
	}

	// Crear una nueva orden
	@DeleteMapping(value = "/orders/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
			//orderService.delete(id);
		orderService.delete(id);
		return new WrapperResponse(true, "success", null).createResponse();
	}
	
	
}
