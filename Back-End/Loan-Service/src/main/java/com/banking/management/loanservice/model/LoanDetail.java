package com.banking.management.loanservice.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "loan_id_seq", sequenceName = "loan_id_seq", initialValue = 100000, allocationSize = 1)
public class LoanDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_id_seq")
	private Long loanId;
	private Long customerId;
	@ManyToOne
	@JoinColumn(name = "loan_type_id")
	private LoanType loanType;
	private BigDecimal loanAmount;
	private int duration;
	private Date date;
	private BigDecimal monthlyInstallment;
	private String collateral;
	@Enumerated(EnumType.STRING)
	private LoanStatus loanStatus;

}
