package com.banking.management.accountservice.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statement {

	private Long accountId;
	private String accountType;
	private Date from;
	private Date to;
	private List<Transaction> transactions;
}
