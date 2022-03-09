package com.robsonmrsp.jwt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robsonmrsp.jwt.model.User;
import com.robsonmrsp.jwt.repository.UserRepository;

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
