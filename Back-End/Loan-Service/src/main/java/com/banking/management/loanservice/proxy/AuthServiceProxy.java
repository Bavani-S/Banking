package com.banking.management.loanservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.banking.management.loanservice.model.AuthResponse;

@FeignClient(name = "auth-service", url = "${feign.client.url.auth}")
public interface AuthServiceProxy {

	@GetMapping("/health")
	public String healthCheck();
	
	@GetMapping("/validateToken")
	public AuthResponse getValidity(@RequestHeader("Authorization") final String token);
	
}
