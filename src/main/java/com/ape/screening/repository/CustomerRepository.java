package com.ape.screening.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ape.screening.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Query("select u from #{#entityName} u where u.lastName = :lastname")
	List<Customer> findByLastName(String lastname);

}
