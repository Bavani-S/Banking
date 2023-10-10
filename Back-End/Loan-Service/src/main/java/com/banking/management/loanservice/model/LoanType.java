package com.banking.management.loanservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanTypeId;
	private String loanType;
	private double rateOfInterest;
	private int maxDuration;
	
}
