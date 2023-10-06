package com.banking.management.accountservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.banking.management.accountservice.model.AuthResponse;

@FeignClient(name = "auth-service", url = "http://localhost:8100/auth/")
public interface AuthServiceProxy {

	@GetMapping("/health")
	public String healthCheck();
	
	@GetMapping("/validateToken")
	public AuthResponse getValidity(@RequestHeader("Authorization") final String token);
	
}
