package com.robsonmrsp.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.robsonmrsp.jwt.model.User;

@Service
public class AuthenticationService {

	@Autowired
	private JWTTokenService tokenService;
	@Autowired
	private AuthenticationManager authManager;

	public String authenticate(String username, String password) throws AuthenticationException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

		Authentication authenticate = authManager.authenticate(usernamePasswordAuthenticationToken);

		return tokenService.generateJWTToken((User) authenticate.getPrincipal());
	}

}
