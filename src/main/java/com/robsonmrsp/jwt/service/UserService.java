package com.robsonmrsp.jwt.service;

import java.util.Optional;

import com.robsonmrsp.jwt.model.User;

public interface UserService {

	Optional<User> getUser(Integer Id);

	Optional<User> findByUsername(String username);

}
