package com.robsonmrsp.jwt.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.robsonmrsp.jwt.http.filter.JWTAuthenticationFilter;
import com.robsonmrsp.jwt.model.User;
import com.robsonmrsp.jwt.service.JWTTokenService;
import com.robsonmrsp.jwt.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTTokenService tokenService;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/auth");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authz -> authz.antMatchers("/auth").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.csrf().disable()
				.authenticationManager(authenticationManager())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JWTAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return authentication -> {
			String username = String.valueOf(authentication.getPrincipal());
			String password = String.valueOf(authentication.getCredentials());
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			System.out.println("WebSecurityConfig.authenticationManager()-" + bCryptPasswordEncoder.encode("123456"));
			
			Optional<User> findByUsername = userService.findByUsername(username);
			if (findByUsername.isEmpty()) {
				throw new BadCredentialsException("1000");
			}
			User user = findByUsername.get();
			if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
				throw new BadCredentialsException("1000");
			}

			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		};
	}
}