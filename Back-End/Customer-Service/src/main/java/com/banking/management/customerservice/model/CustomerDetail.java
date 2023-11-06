package com.banking.management.customerservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence", initialValue = 100001, allocationSize = 1)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
public class CustomerDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
	private Long customerId;
	private String firstName;
	private String lastName;
	@Column(name = "user_name")
	private String userName;
	private String password;
	private String address;
	private String state;
	private String country;
	private String email;
	private String panNumber;
	private String contactNumber;
	private Date dateOfBirth;
	private String idProofType;
	private String idProofNumber;
	private String citizenStatus;
}
