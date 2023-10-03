package com.banking.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.authservice.model.AuthRequest;
import com.banking.authservice.model.UserDetail;
import com.banking.authservice.service.AuthService;
import com.banking.authservice.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private AuthService authService;

	@GetMapping("/health")
	public String healthCheck() {
		return "Auth service is up";

	}
	
	@GetMapping("/all")
	public List<UserDetail> getAllUser() {
		return userService.getAll();

	}

	@PostMapping("/new")
	public String addUser(@RequestBody UserDetail userDetail) {
		return userService.addUser(userDetail);
	}

	@GetMapping("/user/employee")
	@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
	public String employeeDashboard() {
		return "Welcome to employee dashboard!";
	}

	@GetMapping("/user/customer")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public String userDashboard() {
		return "Welcome to customer dashboard!";
	}

	@PostMapping("/authenticate")
	public String getToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return authService.generateToken(authRequest.getUserName());
		}else {
			throw new UsernameNotFoundException("Invalid user request!");
		}

	}

}
