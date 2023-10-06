package com.banking.management.accountservice.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_seq", initialValue = 100000, allocationSize = 1)
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
	private Long transactionId;
	private Long accountId;
	private String transactionType;
	private BigDecimal amount;
	private Long sourceId;
	private Long targetId;
	private Date timeStamp;
	public Transaction(Long accountId, String transactionType, BigDecimal amount, Long sourceId, Long targetId,
			Date timeStamp) {
		super();
		this.accountId = accountId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.timeStamp = timeStamp;
	}
	
}
