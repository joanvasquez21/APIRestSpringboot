package com.jojov.orderapi.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jojov.orderapi.entity.Order;
import com.jojov.orderapi.entity.OrderLine;
import com.jojov.orderapi.entity.Product;
import com.jojov.orderapi.entity.User;
import com.jojov.orderapi.exceptions.GeneralServiceException;
import com.jojov.orderapi.exceptions.NoDataFoundException;
import com.jojov.orderapi.exceptions.ValidateServiceException;
import com.jojov.orderapi.repository.OrderLineRepository;
import com.jojov.orderapi.repository.OrderRepository;
import com.jojov.orderapi.repository.ProductRepository;
import com.jojov.orderapi.security.UserPrincipal;
import com.jojov.orderapi.validators.OrderValidator;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderLineRepository orderLineRepo;

	@Autowired
	private ProductRepository productRepo;

	// Consulta de todos los ordenes(como parametro debe recibir todas las paginas)
	public List<Order> findAll(Pageable page) {

		try {
			return orderRepo.findAll(page).toList();

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	// find by id
	public Order findById(Long id) {
		try {
			return orderRepo.findById(id).orElseThrow(() -> new NoDataFoundException("La orden no existe"));

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	public void delete(Long id) {

		try {
			Order order = orderRepo.findById(id).orElseThrow(() -> new NoDataFoundException("La orden no existe"));

			orderRepo.delete(order);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Transactional
	public Order save(Order order) {
		try {
			// VALIDAR
			OrderValidator.save(order);
			UserPrincipal.getCurrentUser();

			// validamos que el producto exista en la base de datos
			double total = 0;
			for (OrderLine line : order.getLines()) {
				Product product = productRepo.findById(line.getProduct().getId())
						.orElseThrow(() -> new NoDataFoundException("No existe el producto " + line.getProduct()));

				// Para mejor nuestro servicio aprovechamos el for que hemos hecho
				line.setPrecio(product.getPrice());
				line.setTotal(product.getPrice() * line.getQuantity());
				total = total + line.getTotal();
			}

			order.getLines().forEach(line -> line.setOrder(order));

			if (order.getId() == null) { // es nuevo
				order.setUser(order.getUser());
				order.setRegDate(LocalDateTime.now());
				return orderRepo.save(order);
			}
			// actualizacion
			Order saveOrder = orderRepo.findById(order.getId())
					.orElseThrow(() -> new NoDataFoundException("La orden no existe"));
			// Seteamos la fecha
			order.setRegDate(saveOrder.getRegDate());
			// Lo que este en este arreglo seran las lineas que vamos a borrar
			List<OrderLine> deletedLines = saveOrder.getLines();
			deletedLines.removeAll(order.getLines());
			orderLineRepo.deleteAll(deletedLines);

			return orderRepo.save(order);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

}
