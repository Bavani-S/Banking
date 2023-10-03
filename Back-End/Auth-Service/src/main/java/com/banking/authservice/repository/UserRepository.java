package com.banking.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.authservice.model.UserDetail;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, Long> {

	Optional<UserDetail> findByUserName(String userName);
	
	void deleteByUserName(String userName);
}
