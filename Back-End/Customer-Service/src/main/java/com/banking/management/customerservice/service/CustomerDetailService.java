package com.banking.management.customerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.management.customerservice.model.AuthResponse;
import com.banking.management.customerservice.model.CustomerDetail;
import com.banking.management.customerservice.model.UserDetail;
import com.banking.management.customerservice.proxy.AuthServiceProxy;
import com.banking.management.customerservice.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerDetailService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AuthServiceProxy authProxy;
	
	
	public AuthResponse hasPermission(String token) {
		return authProxy.getValidity(token);
	}

	public ResponseEntity<List<CustomerDetail>> getAll(String token) {
		if(hasPermission(token).isValid()) {
			return new ResponseEntity<List<CustomerDetail>>(customerRepository.findAll(), HttpStatus.OK);
		}
		return new ResponseEntity<List<CustomerDetail>>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<CustomerDetail> getCustomer(String token, Long customerId) {
		if(hasPermission(token).isValid()) {
			Optional<CustomerDetail> customer = customerRepository.findById(customerId);
			if(customer.isPresent()) {
				return new ResponseEntity<CustomerDetail>(customer.get(),HttpStatus.OK);
			}
			return new ResponseEntity<CustomerDetail>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomerDetail>(HttpStatus.UNAUTHORIZED);
	}
	
	public ResponseEntity<CustomerDetail> getCustomerByUserName(String token,String userName) {
		if(hasPermission(token).isValid()) {
			Optional<CustomerDetail> customer = customerRepository.findByUserName(userName);
			if(customer.isPresent()) {
				return new ResponseEntity<CustomerDetail>(customer.get(),HttpStatus.OK);
			}
			return new ResponseEntity<CustomerDetail>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomerDetail>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<CustomerDetail> createCustomer(String token, CustomerDetail customerDetail){
		try {
			if(hasPermission(token).isValid()) {
			UserDetail userDetail = new UserDetail(customerDetail.getUserName(), customerDetail.getEmail(), customerDetail.getPassword(), "ROLE_CUSTOMER");
			System.out.println("appuser:"+userDetail.toString());
			ResponseEntity<?> authUser = authProxy.addUser(userDetail);
			System.out.println("response:"+authUser.toString());
			return new ResponseEntity<CustomerDetail>(customerRepository.save(customerDetail), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<CustomerDetail>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<CustomerDetail> updateCustomer(String token, CustomerDetail updateDetail) {
		ResponseEntity<CustomerDetail> existingUserResponse = getCustomer(token, updateDetail.getCustomerId());
		int status = existingUserResponse.getStatusCodeValue();
		if(status == HttpStatus.OK.value()) {
			CustomerDetail existingUser = existingUserResponse.getBody();
			log.info(existingUser.toString());
			existingUser.setAddress(updateDetail.getAddress());
			existingUser.setContactNumber(updateDetail.getContactNumber());
			existingUser.setCountry(updateDetail.getCountry());
			existingUser.setDateOfBirth(updateDetail.getDateOfBirth());
			existingUser.setEmail(updateDetail.getEmail());
			existingUser.setFirstName(updateDetail.getFirstName());
			existingUser.setLastName(updateDetail.getLastName());
			existingUser.setPanNumber(updateDetail.getPanNumber());
			existingUser.setPassword(updateDetail.getPassword());
			existingUser.setState(updateDetail.getState());
			existingUser.setUserName(updateDetail.getUserName());
			existingUser.setIdProofType(updateDetail.getIdProofType());
			existingUser.setIdProofNumber(updateDetail.getIdProofNumber());
			existingUser.setCitizenStatus(updateDetail.getCitizenStatus());
			return new ResponseEntity<CustomerDetail>(customerRepository.save(existingUser),HttpStatus.OK);
		}
		return existingUserResponse;
	}

	public ResponseEntity<?> deleteCustomer(String token, Long customerId) {
		ResponseEntity<CustomerDetail> existingUserResponse = getCustomer(token, customerId);
		int status = existingUserResponse.getStatusCodeValue();
		if(status == HttpStatus.OK.value()) {
			authProxy.deleteUser(token, existingUserResponse.getBody().getUserName());
			customerRepository.deleteById(customerId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return existingUserResponse;
	}
}
