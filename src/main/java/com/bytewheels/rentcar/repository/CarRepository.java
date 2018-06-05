package com.bytewheels.rentcar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bytewheels.rentcar.entity.CarDetails;

@RepositoryRestResource
public interface CarRepository extends CrudRepository<CarDetails, Long> {
	
}

