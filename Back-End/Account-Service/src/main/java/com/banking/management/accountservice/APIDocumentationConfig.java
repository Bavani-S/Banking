package com.banking.management.accountservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class APIDocumentationConfig {
	
	@Value("${application.url}")
	private String applicationUrl;

	@Bean
	public OpenAPI openApiConfig() {
		Server localUrl = new Server();
		localUrl.setUrl("http://localhost:8300/");
		localUrl.setDescription("Local host URL");
		Server appUrl = new Server();
		appUrl.setUrl(applicationUrl);
		appUrl.setDescription("API server URL");
		Info apiInfo = new Info().title("Bank Account Management - API").version("1.0")
				.description("API documentation for customer account management microserivice in Banking Management System");
		return new OpenAPI().info(apiInfo).servers(List.of(localUrl, appUrl));
	}
}
