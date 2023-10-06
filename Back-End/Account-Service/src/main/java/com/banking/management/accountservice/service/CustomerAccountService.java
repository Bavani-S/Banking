package com.banking.management.accountservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.management.accountservice.model.AccountActionDTO;
import com.banking.management.accountservice.model.AccountDetail;
import com.banking.management.accountservice.model.AuthResponse;
import com.banking.management.accountservice.model.Statement;
import com.banking.management.accountservice.model.Transaction;
import com.banking.management.accountservice.proxy.AuthServiceProxy;
import com.banking.management.accountservice.repository.AccountDetailRepository;
import com.banking.management.accountservice.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerAccountService {

	@Autowired
	private AccountDetailRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AuthServiceProxy authProxy;

	public AuthResponse hasPermission(String token) {
		return authProxy.getValidity(token);
	}

	public ResponseEntity<List<AccountDetail>> getAllAccounts(String token) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<AccountDetail>>(accountRepository.findAll(), HttpStatus.OK);
		}
		return new ResponseEntity<List<AccountDetail>>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<List<AccountDetail>> getCustomerAccounts(String token, Long customerId) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<AccountDetail>>(accountRepository.findByCustomerId(customerId),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<AccountDetail>>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<AccountDetail> getAccountById(String token, Long accountId) {
		if (hasPermission(token).isValid()) {
			Optional<AccountDetail> accountDetail = accountRepository.findById(accountId);
			if (accountDetail.isPresent()) {
				return new ResponseEntity<AccountDetail>(accountDetail.get(), HttpStatus.OK);
			}
			return new ResponseEntity<AccountDetail>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AccountDetail>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<List<AccountDetail>> getAccountsByType(String token, String accountType) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<AccountDetail>>(accountRepository.findByAccountType(accountType),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<AccountDetail>>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<AccountDetail> createAccount(String token, AccountDetail accountDetail) {
		try {
			if (hasPermission(token).isValid()) {
				AccountDetail savedAccount = accountRepository.save(accountDetail);
				return new ResponseEntity<AccountDetail>(savedAccount, HttpStatus.CREATED);
			}
			return new ResponseEntity<AccountDetail>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return new ResponseEntity<AccountDetail>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<AccountDetail> depositAmount(String token, AccountActionDTO actionInfo) {
		ResponseEntity<AccountDetail> accountDetail = getAccountById(token, actionInfo.getAccountId());
		if (accountDetail.hasBody()) {
			AccountDetail updateAccount = accountDetail.getBody();
			updateAccount.setCurrentBalance(updateAccount.getCurrentBalance().add(actionInfo.getActionAmount()));
			Transaction transaction = new Transaction(actionInfo.getAccountId(), "Deposit",
					actionInfo.getActionAmount(), actionInfo.getAccountId(), actionInfo.getAccountId(), new Date());
			accountRepository.save(updateAccount);
			transactionRepository.save(transaction);
			return new ResponseEntity<AccountDetail>(updateAccount, HttpStatus.OK);
		}
		return accountDetail;
	}

	public ResponseEntity<AccountDetail> withdrawAmount(String token, AccountActionDTO actionInfo) {
		ResponseEntity<AccountDetail> accountDetail = getAccountById(token, actionInfo.getAccountId());
		if (accountDetail.hasBody()) {
			AccountDetail updateAccount = accountDetail.getBody();
			BigDecimal newBalance = updateAccount.getCurrentBalance().subtract(actionInfo.getActionAmount());
			if (newBalance.compareTo(BigDecimal.valueOf(1000.0)) == -1) {
				return new ResponseEntity<AccountDetail>(HttpStatus.EXPECTATION_FAILED);
			}
			updateAccount.setCurrentBalance(newBalance);
			Transaction transaction = new Transaction(actionInfo.getAccountId(), "Withdraw",
					actionInfo.getActionAmount(), actionInfo.getAccountId(), actionInfo.getAccountId(), new Date());
			accountRepository.save(updateAccount);
			transactionRepository.save(transaction);
			return new ResponseEntity<AccountDetail>(updateAccount, HttpStatus.OK);
		}
		return accountDetail;
	}

	public ResponseEntity<AccountDetail> transfer(String token, AccountActionDTO actionInfo) {
		ResponseEntity<AccountDetail> accountDetail = getAccountById(token, actionInfo.getAccountId());
		ResponseEntity<AccountDetail> targetAccountDetail = getAccountById(token, actionInfo.getTargetAccount());
		if (accountDetail.hasBody() && targetAccountDetail.hasBody()) {
			AccountDetail updateSrcAccount = accountDetail.getBody();
			BigDecimal newSrcBalance = updateSrcAccount.getCurrentBalance().subtract(actionInfo.getActionAmount());
			if (newSrcBalance.compareTo(BigDecimal.valueOf(1000.0)) == -1) {
				return new ResponseEntity<AccountDetail>(HttpStatus.EXPECTATION_FAILED);
			}
			updateSrcAccount.setCurrentBalance(newSrcBalance);
			AccountDetail updateTrgtAccount = targetAccountDetail.getBody();
			updateTrgtAccount
					.setCurrentBalance(updateTrgtAccount.getCurrentBalance().add(actionInfo.getActionAmount()));
			Transaction sourceTransaction = new Transaction(actionInfo.getAccountId(), "Transfer - Credit",
					actionInfo.getActionAmount(), actionInfo.getAccountId(), actionInfo.getTargetAccount(), new Date());
			Transaction targetTransaction = new Transaction(actionInfo.getTargetAccount(), "Transfer - Debit",
					actionInfo.getActionAmount(), actionInfo.getTargetAccount(), actionInfo.getAccountId(), new Date());
			accountRepository.save(updateSrcAccount);
			accountRepository.save(updateTrgtAccount);
			transactionRepository.save(sourceTransaction);
			transactionRepository.save(targetTransaction);
			return new ResponseEntity<AccountDetail>(updateSrcAccount, HttpStatus.OK);
		}
		return accountDetail;
	}

	public ResponseEntity<List<Transaction>> getTransactions(String token, Long accountId) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<Transaction>>(transactionRepository.findTop15ByAccountIdOrderByTimeStampDesc(accountId),HttpStatus.OK);
		}
		return new ResponseEntity<List<Transaction>>(HttpStatus.UNAUTHORIZED);
		}

	public ResponseEntity<List<Transaction>> getStatement(String token, Long accountId, Statement statement) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<Transaction>>(transactionRepository.findByAccountIdAndTimeStampBetween(accountId, statement.getFrom(), statement.getTo()),HttpStatus.OK);
		}
		return new ResponseEntity<List<Transaction>>(HttpStatus.UNAUTHORIZED);
		}

	public ResponseEntity<?> deleteAccount(String token, Long accountId) {
		ResponseEntity<AccountDetail> account = getAccountById(token, accountId);
		if(account.hasBody()) {
			List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
			List<Long> transactionIds = transactions.stream().map(Transaction::getTransactionId).collect(Collectors.toList());
			transactionRepository.deleteAllById(transactionIds);
			accountRepository.deleteById(accountId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return account;
	}

}
