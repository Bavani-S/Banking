package com.banking.management.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {

	private Long id;
	private String userName;
	private String email;
	private String password;
	private String role;
	public UserDetail(String userName, String email, String password, String role) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
