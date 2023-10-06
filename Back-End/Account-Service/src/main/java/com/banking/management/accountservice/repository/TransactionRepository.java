package com.banking.management.accountservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banking.management.accountservice.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	public List<Transaction> findTop15ByAccountIdOrderByTimeStampDesc(Long accountId);
	public List<Transaction> findByAccountIdAndTimeStampBetween(Long accountId, Date startTimeStamp, Date endTimeStamp);
	public List<Transaction> findByAccountId(Long accountId);
	public void deleteByAccountId(Long accountId);
}
