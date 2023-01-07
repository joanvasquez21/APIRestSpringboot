package com.jojov.orderapi.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<E , D> {

	
	//Esta clase tendra la capacidad de convertir de entidad a DTO y de DTO  a entidad
	// y tambien que me permita convertir listas
	
	// Convertir de ENTIDAD A DTO
	public abstract  D fromEntity(E entity);
	
	// Permite convertir de DTO y regresa una entidad
	public abstract E fromDTO(D dto);
	
	// convertir listas
	
	//Recibe una lista de entidades llamada entitys
	// NO sera abstracto
	public List<D> fromEntity(List<E> entitys){
		return	entitys.stream().map(e->fromEntity(e))
						.collect(Collectors.toList());
		
	}
	
	public List<E> fromDTO(List<D> dto){
		return	dto.stream().map(d->fromDTO(d))
						.collect(Collectors.toList());
	}
	
}
