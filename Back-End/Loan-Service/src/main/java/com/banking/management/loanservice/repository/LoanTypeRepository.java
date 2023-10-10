package com.banking.management.loanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.management.loanservice.model.LoanType;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
	
}
