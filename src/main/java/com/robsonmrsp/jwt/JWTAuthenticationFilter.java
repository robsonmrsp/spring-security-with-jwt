package com.robsonmrsp.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
	private final JWTTokenService tokenService;

	public JWTAuthenticationFilter(JWTTokenService tokenService) {
		super();
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String token = getBearerToken(request.getHeader("Authorization"));

		if (tokenService.invalid(token)) {
			SecurityContextHolder.getContext().setAuthentication(null);
			return;
		}
 
		autenticateUser(token);

		filterChain.doFilter(request, response);
	}

	private void autenticateUser(String token) {
		Optional<User> user = tokenService.getUser(token);
		if (user.isPresent()) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
	}

	private String getBearerToken(String headerToken) {
		if (headerToken == null || headerToken.isEmpty() || !headerToken.startsWith("Bearer ")) {
			return null;
		}

		return headerToken.substring(7, headerToken.length());
	}

}
