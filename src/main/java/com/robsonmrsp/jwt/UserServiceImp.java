package com.robsonmrsp.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<User> getUser(Integer Id) {
		Optional<User> byId = userRepository.findById(Id);
		return byId;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
