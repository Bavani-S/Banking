package com.banking.management.accountservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountActionDTO {

	private Long accountId;
	private BigDecimal actionAmount;
	private Long targetAccount;
}
