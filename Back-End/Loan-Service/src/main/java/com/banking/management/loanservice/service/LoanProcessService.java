package com.banking.management.loanservice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.management.loanservice.model.AuthResponse;
import com.banking.management.loanservice.model.EstimateDTO;
import com.banking.management.loanservice.model.LoanDetail;
import com.banking.management.loanservice.model.LoanStatus;
import com.banking.management.loanservice.model.LoanType;
import com.banking.management.loanservice.model.PaymentSchedule;
import com.banking.management.loanservice.proxy.AuthServiceProxy;
import com.banking.management.loanservice.repository.LoanRepository;
import com.banking.management.loanservice.repository.LoanTypeRepository;
import com.banking.management.loanservice.repository.RepaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoanProcessService {

	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private LoanTypeRepository loanTypeRepository;
	@Autowired
	private RepaymentRepository paymentRepository;

	@Autowired
	private AuthServiceProxy authProxy;

	public AuthResponse hasPermission(String token) {
		return authProxy.getValidity(token);
	}

	public ResponseEntity<List<LoanType>> getLoans() {
		return new ResponseEntity<List<LoanType>>(loanTypeRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<List<LoanDetail>> getLoanApplications(String token) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<LoanDetail>>(loanRepository.findAll(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<LoanDetail> getLoanDetails(String token, Long loanId) {
		if (hasPermission(token).isValid()) {
			Optional<LoanDetail> loanDetail = loanRepository.findById(loanId);
			if (loanDetail.isPresent()) {
				return new ResponseEntity<LoanDetail>(loanDetail.get(), HttpStatus.OK);
			}

			return new ResponseEntity<LoanDetail>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LoanDetail>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<List<LoanDetail>> getLoanDetailsByCustomer(String token, Long customerId) {
		if (hasPermission(token).isValid()) {
			List<LoanDetail> loanDetails = loanRepository.findByCustomerId(customerId);
			if (!loanDetails.isEmpty()) {
				return new ResponseEntity<List<LoanDetail>>(loanDetails, HttpStatus.OK);
			}

			return new ResponseEntity<List<LoanDetail>>(loanDetails, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<LoanDetail>>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<EstimateDTO> estimate(EstimateDTO estimateRequest) {
		EstimateDTO estimateResponse = new EstimateDTO();
		estimateResponse.setLoanType(estimateRequest.getLoanType());
		if (estimateRequest.getDuration() == 0) {
			estimateResponse.setDuration(estimateRequest.getLoanType().getMaxDuration());
		} else {
			estimateResponse.setDuration(estimateRequest.getDuration());
		}
		if (estimateRequest.getLoanAmount() != null) {
			estimateResponse.setLoanAmount(estimateRequest.getLoanAmount());
			estimateResponse.setInstallment(calculateEmi(estimateRequest.getLoanAmount(), estimateRequest.getDuration(),
					estimateRequest.getLoanType().getRateOfInterest()));
		} else if (estimateRequest.getInstallment() != null) {
			estimateResponse.setInstallment(estimateRequest.getInstallment());
			estimateResponse.setLoanAmount(calculatePrincipal(estimateRequest.getInstallment(),
					estimateRequest.getDuration(), estimateRequest.getLoanType().getRateOfInterest()));
		} else {
			return new ResponseEntity<EstimateDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<EstimateDTO>(estimateResponse, HttpStatus.OK);
	}

	/*
	 * P x R x (1+R)^N / [(1+R)^N-1]
	 * 
	 * P: Principal loan amount = INR 10,000,00 N: Loan tenure in months = 120
	 * months R: Interest rate per month [7.2/12/100] = 0.006 EMI= INR 10,00,000 *
	 * 0.006 * (1 + 0.006)^120 / ((1 + 0.006)^120 – 1) = INR 11,714.
	 */

	private BigDecimal calculateEmi(BigDecimal loanAmount, int tenure, double rateOfInterestPerAnnum) {
		// MathContext mathContext = new MathContext(6);
		double interestRatePerMonth = rateOfInterestPerAnnum / (12 * 100);
		BigDecimal emi = loanAmount.multiply(BigDecimal.valueOf(interestRatePerMonth
				* Math.pow((1 + interestRatePerMonth), tenure) / (Math.pow((1 + interestRatePerMonth), tenure) - 1)))
				.setScale(2, RoundingMode.HALF_UP);
		return emi;
	}

	private BigDecimal calculatePrincipal(BigDecimal installment, int tenure, double rateOfInterestPerAnnum) {
		double interestRatePerMonth = rateOfInterestPerAnnum / (12 * 100);
		BigDecimal loanAmount = installment.divide(BigDecimal.valueOf(interestRatePerMonth
				* Math.pow((1 + interestRatePerMonth), tenure) / (Math.pow((1 + interestRatePerMonth), tenure) - 1)), 2,
				RoundingMode.HALF_UP);
		return loanAmount;
	}

	public ResponseEntity<LoanDetail> applyLoan(String token, LoanDetail loanDetail) {
		if (hasPermission(token).isValid()) {
			try {
				loanDetail.setLoanStatus(LoanStatus.NEW);
				loanDetail.setMonthlyInstallment(calculateEmi(loanDetail.getLoanAmount(), loanDetail.getDuration(),
						loanDetail.getLoanType().getRateOfInterest()));
				loanDetail.setDate(new Date());
				return new ResponseEntity<LoanDetail>(loanRepository.save(loanDetail), HttpStatus.CREATED);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			return new ResponseEntity<LoanDetail>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<LoanDetail> confirmApplication(String token, Long loanId) {
		ResponseEntity<LoanDetail> loanDetails = getLoanDetails(token, loanId);
		if (loanDetails.hasBody()) {
			LoanDetail confirmLoan = loanDetails.getBody();
			confirmLoan.setLoanStatus(LoanStatus.PENDINGFORREVIEW);
			confirmLoan.setApplyDate(new Date());
			confirmLoan.setDate(new Date());
			return new ResponseEntity<LoanDetail>(loanRepository.save(confirmLoan), HttpStatus.OK);
		}
		return loanDetails;
	}

	public ResponseEntity<LoanDetail> withdrawApplication(String token, Long loanId) {
		ResponseEntity<LoanDetail> loanDetails = getLoanDetails(token, loanId);
		if (loanDetails.hasBody()) {
			LoanDetail confirmLoan = loanDetails.getBody();
			confirmLoan.setLoanStatus(LoanStatus.WITHDRAWN);
			confirmLoan.setDate(new Date());
			return new ResponseEntity<LoanDetail>(loanRepository.save(confirmLoan), HttpStatus.OK);
		}
		return loanDetails;
	}

	public ResponseEntity<List<LoanDetail>> reviewApplications(String token) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<LoanDetail>>(loanRepository.findByLoanStatus(LoanStatus.PENDINGFORREVIEW),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<LoanDetail> reviewApplication(String token, Long loanId) {
		ResponseEntity<LoanDetail> loanDetails = getLoanDetails(token, loanId);
		if (loanDetails.hasBody()) {
			LoanDetail confirmLoan = loanDetails.getBody();
			confirmLoan.setLoanStatus(LoanStatus.PENDINGFORAPPROVAL);
			confirmLoan.setDate(new Date());
			return new ResponseEntity<LoanDetail>(loanRepository.save(confirmLoan), HttpStatus.OK);
		}
		return loanDetails;
	}

	public ResponseEntity<List<LoanDetail>> approveApplications(String token) {
		if (hasPermission(token).isValid()) {
			return new ResponseEntity<List<LoanDetail>>(loanRepository.findByLoanStatus(LoanStatus.PENDINGFORAPPROVAL),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<LoanDetail>>(HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<LoanDetail> approveApplication(String token, Long loanId) {
		ResponseEntity<LoanDetail> loanDetails = getLoanDetails(token, loanId);
		if (loanDetails.hasBody()) {
			LoanDetail confirmLoan = loanDetails.getBody();
			confirmLoan.setLoanStatus(LoanStatus.APPROVED);
			confirmLoan.setDate(new Date());
			// System.out.println(LocalDate.now().plusMonths(1));
			createPaymentSchedule(confirmLoan, LocalDate.now());
			confirmLoan.setDate(new Date());
			return new ResponseEntity<LoanDetail>(loanRepository.save(confirmLoan), HttpStatus.OK);
		}
		return loanDetails;
	}

	public void createPaymentSchedule(LoanDetail loanDetails, LocalDate date) {
		int tenure = loanDetails.getDuration();
		System.out.println("tenure" + tenure);
		BigDecimal remainingPrincipal = loanDetails.getLoanAmount();
		System.out.println("remPrin:" + remainingPrincipal);
		BigDecimal emi = calculateEmi(remainingPrincipal, tenure, loanDetails.getLoanType().getRateOfInterest());
		System.out.println("emi" + emi);
		BigDecimal total = loanDetails.getLoanAmount().add(emi.multiply(BigDecimal.valueOf(tenure)));
		System.out.println("total:" + total);
		List<PaymentSchedule> installmentDetails = new ArrayList<>();
		while (tenure != 0) {
			date = date.plusMonths(1);
			BigDecimal interestComponent = remainingPrincipal
					.multiply(BigDecimal.valueOf(loanDetails.getLoanType().getRateOfInterest() / (100 * 12 * 12)));
			BigDecimal monthlyPrincipal = emi.subtract(interestComponent);
			PaymentSchedule paymentSchedule = PaymentSchedule.builder().loanId(loanDetails.getLoanId())
					.month(new SimpleDateFormat("MMM yyyy").format(date)).monthlyPrincipal(monthlyPrincipal)
					.monthlyInterest(interestComponent).installment(emi).paymentStatus("yet to be paid").balance(total)
					.build();
			total = total.subtract(emi);
			tenure = tenure - 1;
			remainingPrincipal = remainingPrincipal.subtract(monthlyPrincipal);
			installmentDetails.add(paymentSchedule);
		}
		System.out.println("list: " + installmentDetails);
		paymentRepository.saveAll(installmentDetails);
		System.out.println("schedule saved");
	}

	public ResponseEntity<LoanDetail> rejectApplication(String token, Long loanId) {
		ResponseEntity<LoanDetail> loanDetails = getLoanDetails(token, loanId);
		if (loanDetails.hasBody()) {
			LoanDetail confirmLoan = loanDetails.getBody();
			confirmLoan.setDate(new Date());
			confirmLoan.setLoanStatus(LoanStatus.REJECTED);
			return new ResponseEntity<LoanDetail>(loanRepository.save(confirmLoan), HttpStatus.OK);
		}
		return loanDetails;
	}

	public ResponseEntity<List<PaymentSchedule>> paymentSchedule(String token, Long loanId) {
		ResponseEntity<LoanDetail> loanDetails = getLoanDetails(token, loanId);
		if (loanDetails.hasBody()) {
			return new ResponseEntity<List<PaymentSchedule>>(paymentRepository.findByLoanId(loanId), HttpStatus.OK);
		}
		return new ResponseEntity<List<PaymentSchedule>>(loanDetails.getStatusCode());
	}

}
