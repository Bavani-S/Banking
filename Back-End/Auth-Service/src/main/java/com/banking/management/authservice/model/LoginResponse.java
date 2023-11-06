package com.banking.management.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

	private String userName;
	private String token;
	private String roles;
	private ResultMessage message;

}
