package com.banking.management.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.management.customerservice.model.CustomerDetail;
import com.banking.management.customerservice.proxy.AuthServiceProxy;
import com.banking.management.customerservice.service.CustomerDetailService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins="*")
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

	@GetMapping("/all")
	public ResponseEntity<List<CustomerDetail>> getAllCustomers(@RequestHeader("Authorization") String token) {
		return customerService.getAll(token);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDetail> getCustomer(@RequestHeader("Authorization") String token,
			@PathVariable Long customerId) {
		return customerService.getCustomer(token, customerId);
	}
	
	@GetMapping("/profile/{userName}")
	public ResponseEntity<CustomerDetail> getCustomerByUserName(@RequestHeader("Authorization") String token,
			@PathVariable String userName) {
		return customerService.getCustomerByUserName(token, userName);
	}


	@PostMapping("/create")
	public ResponseEntity<CustomerDetail> createCustomer(@RequestHeader("Authorization") String token,
			@RequestBody CustomerDetail customerDetail) {
		return customerService.createCustomer(token, customerDetail);
	}

	@PutMapping("/{customerId}/update")
	public ResponseEntity<CustomerDetail> updateCustomer(@RequestHeader("Authorization") String token,
			@PathVariable Long customerId, @RequestBody CustomerDetail updateDetail) {
		return customerService.updateCustomer(token, updateDetail);
	}

	@DeleteMapping("/{customerId}/delete")
	public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String token,
			@PathVariable Long customerId) {
		return customerService.deleteCustomer(token, customerId);
	}

	@GetMapping("/auth-check")
	@CircuitBreaker(name = "auth-api", fallbackMethod = "fallbackMethod")
	public String authProxyhealth() {
		log.info("auth proxy api call");
		return authProxy.healthCheck();
	}

	@GetMapping("/auth-check-retry")
	@Retry(name = "auth-api", fallbackMethod = "fallbackMethod")
	public String authProxyhealthRetry() {
		log.info("auth proxy api call");
		return authProxy.healthCheck();
	}

	public String fallbackMethod(Exception e) {
		return "Auth Service not available!";
	}
}
