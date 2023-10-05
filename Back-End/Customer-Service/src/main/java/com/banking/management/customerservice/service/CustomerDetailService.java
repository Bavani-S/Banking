package com.banking.management.customerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.management.customerservice.model.CustomerDetail;
import com.banking.management.customerservice.repository.CustomerRepository;

@Service
public class CustomerDetailService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<CustomerDetail> getAll() {
		return customerRepository.findAll();
	}

	public CustomerDetail getCustomer(Long customerId) {
		return customerRepository.findById(customerId).get();
	}

	public CustomerDetail createCustomer(CustomerDetail customerDetail) throws Exception{
		return customerRepository.save(customerDetail);
	}

	public CustomerDetail updateCustomer(CustomerDetail updateDetail) {
		CustomerDetail existingUser = getCustomer(updateDetail.getCustomerId());
		if (existingUser!= null) {
		existingUser.setAccount(updateDetail.getAccount());
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
		return customerRepository.save(existingUser);
		}
		return null;
	}

	public void deleteCustomer(Long customerId) {
		customerRepository.deleteById(customerId);
		
	}
}
