package com.banking.management.customerservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.experimental.FieldNameConstants;

@FeignClient(name = "auth-service", url = "http://localhost:8100/auth/")
public interface AuthServiceProxy {

	@GetMapping("/health")
	public String healthCheck();
}
