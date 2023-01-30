package com.jojov.orderapi.converters;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


import com.jojov.orderapi.dtos.OrderDTO;
import com.jojov.orderapi.dtos.OrderLineDTO;
import com.jojov.orderapi.entity.Order;
import com.jojov.orderapi.entity.OrderLine;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderConverter  extends AbstractConverter<Order, OrderDTO>{

	private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");	
	private ProductConverter productConverter = new ProductConverter();
	private UserConverter userConverter;
		
	

	@Override
	public OrderDTO fromEntity(Order entity) {
		// TODO Auto-generated method stub
		if(entity == null) return null;
		List<OrderLineDTO> lines = fromOrderLineEntity(entity.getLines());
		
		
		
		return OrderDTO.builder()
				.id(entity.getId())
				.lines(lines)
				.regDate(entity.getRegDate().format(dateTimeFormat))
				.total(entity.getTotal())
				.user(userConverter.fromEntity(entity.getUser()))
		        .build();
	}

	@Override
	public Order fromDTO(OrderDTO dto) {
		if (dto == null) return null;

		List<OrderLine> lines = fromOrderLineDTO(dto.getLines());

		return Order.builder()
				.id(dto.getId())
				.lines(lines)
				.total(dto.getTotal())
				.user(userConverter.fromDTO(dto.getUser()))
				.build();
	}

	// Como convertir lineas
	// Retornar una nueva lista mapeada a partir de las lineas de tipo entidad se va a convertir a  lineas de tipo dto

	private List<OrderLineDTO> fromOrderLineEntity(List<OrderLine> lines) {
		if(lines == null) return null;
		
		return lines.stream().map(line -> {
			return OrderLineDTO.builder()
					.id(line.getId())
					.price(line.getPrice())
					.product(productConverter.fromEntity(line.getProduct()))
					.quantity(line.getQuantity())
					.total(line.getTotal())
					.build();
		})
		.collect(Collectors.toList());
	}
	
	private List<OrderLine> fromOrderLineDTO(List<OrderLineDTO> lines) {
		if(lines == null) return null;
		
		return lines.stream().map(line -> {
			return OrderLine.builder()
					.id(line.getId())
					.price(line.getPrice())
					.product(productConverter.fromDTO(line.getProduct()))
					.quantity(line.getQuantity())
					.total(line.getTotal())
					.build();
		})
		.collect(Collectors.toList());
	}
	
}
