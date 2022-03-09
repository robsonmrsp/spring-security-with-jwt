package com.robsonmrsp.jwt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String geta() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "Hello, " + authentication.getName();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String posta() {
		return "chegou! post!!";

	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public String put() {
		return "chegou! post!!";
	}
}
