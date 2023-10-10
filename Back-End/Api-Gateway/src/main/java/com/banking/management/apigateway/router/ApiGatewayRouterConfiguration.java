package com.banking.management.apigateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ApiGatewayRouterConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder routeBuilder) {
		return routeBuilder.routes()
				.route(p -> p.path("/auth/**").uri("lb://auth-service"))
				.route(p-> p.path("/user/**").uri("lb://customer-service"))
				.route(p-> p.path("/account/**").uri("lb://account-service"))
				.route(p-> p.path("/loan/**").uri("lb://=loan-service"))
				.build();			
	}
}
