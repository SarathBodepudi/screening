package com.ape.screening.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ape.screening.model.Customer;
import com.ape.screening.repository.CustomerRepository;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	// Create a customer
	@PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer customer2 = (Customer) customerRepository.save(new Customer(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmailId()));
		return new ResponseEntity<>(customerRepository.save(customer2), HttpStatus.CREATED);
	}
	
	// Retrieve a Customer by Id.
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") long id) {
		Optional<Customer> customerData = customerRepository.findById(id);
		if(customerData.isPresent()) {
			return new ResponseEntity<>(customerRepository.getById(id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// Retrieve all Customers
	@GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	// Update a customers email.
	@PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		
		Optional<Customer> customerData = customerRepository.findById(customer.getId());
		
		if(customerData.isPresent()) {
			Customer customerToBeUpdated = customerRepository.getById(customer.getId());
			customerToBeUpdated.setEmailId(customer.getEmailId());
			
			return new ResponseEntity<>(customerRepository.save(customerToBeUpdated), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// Delete Customer
	@DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public  ResponseEntity<Customer> deleteCustomer(@PathVariable(value = "id") long id) {
		customerRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// Search for customer by last name
	 @GetMapping(value = "/lastname/{lastname}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	 public List<Customer> findByLastName(@PathVariable(value = "lastname") String lastname) {
		 return customerRepository.findByLastName(lastname);
	 }
}
