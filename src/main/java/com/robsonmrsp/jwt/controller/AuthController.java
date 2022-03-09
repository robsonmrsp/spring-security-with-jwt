package com.robsonmrsp.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robsonmrsp.jwt.data.JsonLogin;
import com.robsonmrsp.jwt.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> autenticar(@RequestBody JsonLogin login) {

		try {
			String tokenJWT = authenticationService.authenticate(login.getUsername(), login.getPassword());
			return ResponseEntity.ok(tokenJWT);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
