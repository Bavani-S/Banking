package com.banking.authservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.banking.authservice.model.AuthRequest;
import com.banking.authservice.model.AuthResponse;
import com.banking.authservice.model.LoginResponse;
import com.banking.authservice.model.ResultMessage;
import com.banking.authservice.model.UserDetail;
import com.banking.authservice.service.AuthService;
import com.banking.authservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@CrossOrigin(origins="*")
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private AuthService authService;
	
	@Value("${central.config:Unable to connect to central config server}")
	private String centralConfig;

	@GetMapping("/health")
	public String healthCheck() {
		return "Auth service is up "+ this.centralConfig;

	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
	public List<UserDetail> getAllUser() {
		return userService.getAll();

	}

//	@PostMapping("/new")
//	public String addUser(@RequestBody UserDetail userDetail) {
//		return userService.addUser(userDetail);
//	}

	@GetMapping("/employee")
	@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
	public String employeeAccess() {
		return "Welcome to employee dashboard!";
	}

	@GetMapping("/customer")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public String customerAccess() {
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
	
	@PostMapping("/new")
	//@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
	public ResponseEntity<?> addUser(@RequestBody UserDetail userDetail) {
		return new ResponseEntity<>(userService.addUser(userDetail),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest loginRequest) throws Exception {
		String token = getToken(loginRequest);
		LoginResponse loginResponseDTO;
		if(token.isEmpty()) {
			loginResponseDTO = new LoginResponse(loginRequest.getUserName(),token,new ResultMessage(401,"Invalid Credentials!"));
		}else {
			loginResponseDTO = new LoginResponse(loginRequest.getUserName(),token,new ResultMessage(200,"Success"));
			log.info("Logged in ->"+loginRequest.getUserName());
		}
		return ResponseEntity.ok().body(loginResponseDTO);
	}
	
	@GetMapping("/validateToken")
	public AuthResponse getValidity(@RequestHeader("Authorization") final String token) {
		log.info("Token Validation ->{}",token);
		return authService.validate(token);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	//@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable String id) {

		System.out.println("Starting deletion of->" + id);
		userService.deleteUser(id);
		System.out.println("Deleted");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
