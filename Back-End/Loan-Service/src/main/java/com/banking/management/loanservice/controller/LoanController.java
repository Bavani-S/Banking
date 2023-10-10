package com.banking.management.loanservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.management.loanservice.model.EstimateDTO;
import com.banking.management.loanservice.model.LoanDetail;
import com.banking.management.loanservice.model.LoanType;
import com.banking.management.loanservice.model.PaymentSchedule;
import com.banking.management.loanservice.service.LoanProcessService;

@RequestMapping("/loan")
@RestController
public class LoanController {

	@Autowired
	private LoanProcessService loanService;

	@GetMapping("/health")
	public String health() {
		return "Loan service is up";
	}

	@GetMapping("/all")
	public ResponseEntity<List<LoanType>> getLoans() {
		return loanService.getLoans();
	}

	@GetMapping("/all-loans")
	public ResponseEntity<List<LoanDetail>> getLoanApplications(@RequestHeader("Authorization") String token) {
		return loanService.getLoanApplications(token);
	}

	@GetMapping("/{loanId}")
	public ResponseEntity<LoanDetail> getLoanDetails(@RequestHeader("Authorization") String token,
			@PathVariable Long loanId) {
		return loanService.getLoanDetails(token, loanId);
	}

	@GetMapping("/{customerId}/loans")
	public ResponseEntity<List<LoanDetail>> getLoanDetailsByCustomer(@RequestHeader("Authorization") String token,
			@PathVariable Long customerId) {
		return loanService.getLoanDetailsByCustomer(token, customerId);
	}

	@PostMapping("/estimate")
	public ResponseEntity<EstimateDTO> estimate(@RequestBody EstimateDTO estimateRequest) {
		return loanService.estimate(estimateRequest);
	}

	@PostMapping("/apply")
	public ResponseEntity<LoanDetail> applyLoan(@RequestHeader("Authorization") String token,
			@RequestBody LoanDetail loanDetail) {
		return loanService.applyLoan(token, loanDetail);
	}

	@PutMapping("/confirm/{loanId}")
	public ResponseEntity<LoanDetail> confirmApplication(@RequestHeader("Authorization") String token,
			@PathVariable Long loanId) {
		return loanService.confirmApplication(token, loanId);
	}

	@PutMapping("/withdraw/{loanId}")
	public ResponseEntity<LoanDetail> withdrawApplication(@RequestHeader("Authorization") String token,
			@PathVariable Long loanId) {
		return loanService.withdrawApplication(token, loanId);
	}

	@GetMapping("/review")
	public ResponseEntity<List<LoanDetail>> reviewAll(@RequestHeader("Authorization") String token) {
		return loanService.reviewApplications(token);
	}

	@PutMapping("/review/{loanId}")
	public ResponseEntity<LoanDetail> reviewApplication(@RequestHeader("Authorization") String token,
			@PathVariable Long loanId) {
		return loanService.reviewApplication(token, loanId);
	}

	@GetMapping("/approve")
	public ResponseEntity<List<LoanDetail>> approveAll(@RequestHeader("Authorization") String token) {
		return loanService.approveApplications(token);
	}

	@PutMapping("/approve/{loanId}")
	public ResponseEntity<LoanDetail> approveApplication(@RequestHeader("Authorization") String token,
			@PathVariable Long loanId) {
		return loanService.approveApplication(token, loanId);
	}

	@PutMapping("/reject/{loanId}")
	public ResponseEntity<LoanDetail> rejectApplication(@RequestHeader("Authorization") String token,
			@PathVariable Long loanId) {
		return loanService.rejectApplication(token, loanId);
	}

	@GetMapping("/{loanId}/payment-schedule")
	public ResponseEntity<List<PaymentSchedule>> paymentSchedule(@RequestHeader("Authorization") String token,
			@PathVariable Long loanId) {
		return loanService.paymentSchedule(token, loanId);
	}
}
