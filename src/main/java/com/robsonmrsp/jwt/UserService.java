package com.robsonmrsp.jwt;

import java.util.Optional;

public interface UserService {

	Optional<User> getUser(Integer Id);

	Optional<User> findByUsername(String username);

}
