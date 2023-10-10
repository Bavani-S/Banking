package com.banking.management.loanservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateDTO {

	private LoanType loanType;
	private BigDecimal loanAmount;
	private int duration;
	private BigDecimal installment;
}
