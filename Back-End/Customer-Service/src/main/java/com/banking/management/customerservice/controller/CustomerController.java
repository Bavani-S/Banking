package com.banking.management.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.management.customerservice.model.CustomerDetail;
import com.banking.management.customerservice.proxy.AuthServiceProxy;
import com.banking.management.customerservice.service.CustomerDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class CustomerController {

	@Autowired
	private CustomerDetailService customerService;
	
	@Autowired
	private AuthServiceProxy authProxy;

	@GetMapping("/health")
	public String health() {
		return "Customer service is up";
	}
	
	@GetMapping("/auth-check")
	public String authProxyhealth() {
		return authProxy.healthCheck();
	}

	@GetMapping("/all")
	public List<CustomerDetail> getAllCustomers() {
		return customerService.getAll();
	}

	@GetMapping("/{customerId}")
	public CustomerDetail getCustomer(@PathVariable Long customerId) {
		return customerService.getCustomer(customerId);
	}

	@PostMapping("/create")
	public ResponseEntity<CustomerDetail> createCustomer(@RequestBody CustomerDetail customerDetail) {
		CustomerDetail createdCustomer;
		try {
			createdCustomer = customerService.createCustomer(customerDetail);
			return new ResponseEntity<CustomerDetail>(createdCustomer, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/{customerId}/update")
	public ResponseEntity<CustomerDetail> updateCustomer(@PathVariable Long customerId,
			@RequestBody CustomerDetail updateDetail) {
		CustomerDetail updatedCustomer = customerService.updateCustomer(updateDetail);
		if (updatedCustomer != null) {
			return new ResponseEntity<CustomerDetail>(updatedCustomer, HttpStatus.CREATED);
		}
		return new ResponseEntity<CustomerDetail>(updatedCustomer, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{customerId}/delete")
	public ResponseEntity deleteCustomer(@PathVariable Long customerId) {
		if (customerService.getCustomer(customerId) != null) {
			customerService.deleteCustomer(customerId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
