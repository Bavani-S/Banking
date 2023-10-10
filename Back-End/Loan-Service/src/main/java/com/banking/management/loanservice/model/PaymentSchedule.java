package com.banking.management.loanservice.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long emiId;
	private Long loanId;
	private String month;
	private BigDecimal monthlyPrincipal;
	private BigDecimal monthlyInterest;
	private BigDecimal installment;
	private String paymentStatus;
	private BigDecimal balance;
}
