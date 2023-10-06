package com.banking.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.authservice.model.UserDetail;
import com.banking.authservice.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	public UserDetail addUser(UserDetail userDetail) {
		userDetail.setPassword(encoder.encode(userDetail.getPassword()));
		return userRepository.save(userDetail);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDetail> userDetail = userRepository.findByUserName(username);
		return userDetail.map(UserInfo::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for -> " + username));
	}

	public List<UserDetail> getAll() {
		return userRepository.findAll();
	}

	public void deleteUser(String userName) {
		UserDetail userDetail = userRepository.findByUserName(userName).get();
		userRepository.deleteById(userDetail.getId());
	}

}
