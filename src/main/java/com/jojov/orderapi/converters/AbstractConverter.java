package com.jojov.orderapi.converters;

import java.util.List;
import java.util.stream.Collectors;


public abstract class AbstractConverter<ENTIDAD , DTO> {

	
	//Esta clase tendra la capacidad de convertir de entidad a DTO y de DTO  a entidad
	// y tambien que me permita convertir listas
	
	// Convertir de ENTIDAD A DTO - REGRESA UN DTO DESDE ENTIDAD( E entity)
	public abstract  DTO fromEntity(ENTIDAD entity);
	
	// Permite convertir de DTO y regresa una entidad, recibe un DTO y regresa una entidad 
	public abstract ENTIDAD fromDTO(DTO dto);
	
	// convertir listas
	
	//Recibe una lista de entidades llamada entitys
	// NO sera abstracto

	
	// Convertir listas, este no es abstracto, este no es abstracto 
	//Si ya tenemos la logica para convertir objeto individual, lo podemos reutilizar para convertir todo el objeto
	//Convertiras a DTO la lista de Entidades
	public List<DTO> fromEntity(List<ENTIDAD> entitys){
		return	entitys.stream() //por cada entidad(e), has una conversion de entidad
				.map(e->fromEntity(e)) // regresa un DTO y luego hacemos un collectors 
						.collect(Collectors.toList());
	}
	
	public List<ENTIDAD> fromDTO(List<DTO> dto){
		return	dto.stream()
				.map(d->fromDTO(d))
						.collect(Collectors.toList());
	}
	
}
