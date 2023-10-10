package com.banking.management.loanservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.management.loanservice.model.LoanDetail;
import com.banking.management.loanservice.model.LoanStatus;

@Repository
public interface LoanRepository extends JpaRepository<LoanDetail, Long> {

	public List<LoanDetail> findByCustomerId(Long customerId);
 
	public List<LoanDetail> findByLoanStatus(LoanStatus loanStatus);
}
