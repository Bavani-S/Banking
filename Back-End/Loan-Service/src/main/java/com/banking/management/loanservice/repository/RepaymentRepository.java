package com.banking.management.loanservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.management.loanservice.model.PaymentSchedule;

@Repository
public interface RepaymentRepository extends JpaRepository<PaymentSchedule, Long> {

	public List<PaymentSchedule> findByLoanId(Long loanId);
}
