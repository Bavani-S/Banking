package com.banking.management.accountservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.management.accountservice.model.AccountActionDTO;
import com.banking.management.accountservice.model.AccountDetail;
import com.banking.management.accountservice.model.Statement;
import com.banking.management.accountservice.model.Transaction;
import com.banking.management.accountservice.service.CustomerAccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private CustomerAccountService accountService;

	@GetMapping("/health")
	public String health() {
		return "Account service is up";
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<AccountDetail>> getAllAccounts(@RequestHeader("Authorization") String token){
		return accountService.getAllAccounts(token);
	}
	
	@GetMapping("/{customerId}/accounts")
	public ResponseEntity<List<AccountDetail>> getCustomerAccounts(@RequestHeader("Authorization") String token, @PathVariable Long customerId){
		return accountService.getCustomerAccounts(token, customerId);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDetail> getAccountById(@RequestHeader("Authorization") String token, @PathVariable Long accountId){
		return accountService.getAccountById(token, accountId);
	}
	
	@GetMapping("/type/{accountType}")
	public ResponseEntity<List<AccountDetail>> getAccountsByType(@RequestHeader("Authorization") String token, @PathVariable String accountType){
		return accountService.getAccountsByType(token, accountType);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AccountDetail> createAccount(@RequestHeader("Authorization") String token, @RequestBody AccountDetail accountDetail){
		return accountService.createAccount(token, accountDetail);
	}
	
	@PutMapping("/action/deposit")
	public ResponseEntity<AccountDetail> depositAmount(@RequestHeader("Authorization") String token, @RequestBody AccountActionDTO actionInfo){
		return accountService.depositAmount(token, actionInfo);
	}
	
	@PutMapping("/action/withdraw")
	public ResponseEntity<AccountDetail> withdrawAmount(@RequestHeader("Authorization") String token, @RequestBody AccountActionDTO actionInfo){
		return accountService.withdrawAmount(token, actionInfo);
	}
	
	@PutMapping("/action/transfer")
	public ResponseEntity<AccountDetail> transfer(@RequestHeader("Authorization") String token, @RequestBody AccountActionDTO actionInfo){
		return accountService.transfer(token, actionInfo);
	}
	
	@GetMapping("/{accountId}/transaction")
	public ResponseEntity<List<Transaction>> getTransactions(@RequestHeader("Authorization") String token, @PathVariable Long accountId){
		return accountService.getTransactions(token, accountId);
	}
	
	@GetMapping("/{accountId}/statement")
	public ResponseEntity<List<Transaction>> getStatement(@RequestHeader("Authorization") String token, @PathVariable Long accountId, @RequestBody Statement statement){
		return accountService.getStatement(token, accountId, statement);
	}
	
	@DeleteMapping("/{accountId}/delete")
	public ResponseEntity<?> deleteAccount(@RequestHeader("Authorization") String token, @PathVariable Long accountId){
		return accountService.deleteAccount(token, accountId);
	}
}