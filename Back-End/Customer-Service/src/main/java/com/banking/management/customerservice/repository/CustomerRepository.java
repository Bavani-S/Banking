package com.banking.management.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.management.customerservice.model.CustomerDetail;

public interface CustomerRepository extends JpaRepository<CustomerDetail, Long> {

}
