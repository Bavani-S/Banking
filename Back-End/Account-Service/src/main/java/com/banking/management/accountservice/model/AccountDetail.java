package com.banking.management.accountservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", initialValue = 100000000, allocationSize = 1)
public class AccountDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
	private Long accountId;
	private String accountType;
	private Long customerId;
	private String userName;
	private BigDecimal openingAmount;
	private BigDecimal currentBalance;
	private LocalDate openingDate;
	private String branchName;

}
