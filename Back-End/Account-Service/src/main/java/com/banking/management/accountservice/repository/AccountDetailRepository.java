package com.banking.management.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.management.accountservice.model.AccountDetail;

public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {
	
	public List<AccountDetail> findByUserName(String userName);
	public List<AccountDetail> findByAccountType(String accountType);
	public List<AccountDetail> findByCustomerId(Long customerId);

}
