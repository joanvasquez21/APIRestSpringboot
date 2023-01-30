package com.jojov.orderapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jojov.orderapi.entity.Product;
import com.jojov.orderapi.exceptions.GeneralServiceException;
import com.jojov.orderapi.exceptions.NoDataFoundException;
import com.jojov.orderapi.exceptions.ValidateServiceException;
import com.jojov.orderapi.repository.ProductRepository;
import com.jojov.orderapi.validators.ProductValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class productService {

	@Autowired
	private ProductRepository productRepo;

	public Product findById(Long productId) {
		try {
			Product product = productRepo.findById(productId)
					.orElseThrow(() -> new NoDataFoundException("No existe el producto"));
			return product;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Transactional
	public void delete(Long productId) {

		try {
			// Primero traemos el producto que queremos borrar, para saber si existe
			Product product = productRepo.findById(productId)
					.orElseThrow(() -> new NoDataFoundException("No existe el producto"));
			// En caso de que exita, lo elimina
			productRepo.delete(product);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}

	}

	public List<Product> findAll(Pageable pageable) {

		try {
			List<Product> products = productRepo.findAll(pageable).toList();
		return products;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
		
		
		
	}

	public Product save(Product product) {

		try {
			ProductValidator.save(product);

			if (product.getId() == null) {
				Product newProduct = productRepo.save(product);
				return newProduct;
			}

			Product productUpdate = productRepo.findById(product.getId())
					.orElseThrow(() -> new NoDataFoundException("No existe el producto"));

			productUpdate.setName(product.getName());
			productUpdate.setPrice(product.getPrice());

			productRepo.save(productUpdate);

			return productUpdate;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
		
		
		

	}

}
