package com.bytewheels.rentcar.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bytewheels.rentcar.entity.UserDetails;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<UserDetails, Long> {

	@Query("select u from UserDetails u where u.emailId = :emailId")
	public Iterable<UserDetails> findUserData(@Param("emailId") String emailId);
	
	//@Query("select u.carId from UserDetails u where u.startDate BETWEEN :startDate AND :endDate OR u.endDate BETWEEN :startDate AND :endDate")
	//public Iterable<Long> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
