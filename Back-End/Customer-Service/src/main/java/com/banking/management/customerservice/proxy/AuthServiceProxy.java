package com.banking.management.customerservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.banking.management.customerservice.model.AuthRequest;
import com.banking.management.customerservice.model.AuthResponse;
import com.banking.management.customerservice.model.LoginResponse;
import com.banking.management.customerservice.model.UserDetail;

@FeignClient(name = "auth-service", url = "${feign.client.url.auth}")
public interface AuthServiceProxy {

	@GetMapping("/health")
	public String healthCheck();
	
	@PostMapping("/new")
	public ResponseEntity<?> addUser(@RequestBody UserDetail userDetail);
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest loginRequest);
	
	@GetMapping("/validateToken")
	public AuthResponse getValidity(@RequestHeader("Authorization") final String token);
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable String id);
}
