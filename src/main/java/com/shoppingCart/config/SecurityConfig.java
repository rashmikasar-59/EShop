package com.shoppingCart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final UserDetailsService userDetailsService;

	// =========================================================
	// PASSWORD ENCODER
	// =========================================================

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// =========================================================
	// AUTHENTICATION PROVIDER
	// =========================================================

	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	// =========================================================
	// AUTHENTICATION MANAGER
	// =========================================================

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();
	}

	// =========================================================
	// SECURITY FILTER CHAIN
	// =========================================================

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http

				// Disable CSRF because this is a REST API
				.csrf(csrf -> csrf.disable())

				// JWT is stateless
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization rules
				.authorizeHttpRequests(auth -> auth

						// Authentication APIs
						.requestMatchers("/api/v1/auth/**").permitAll()

						// Public product APIs
						.requestMatchers("/api/v1/products/**").permitAll()

						// Public category APIs
						.requestMatchers("/api/v1/categories/**").permitAll()

						// Public image download API
						.requestMatchers("/api/v1/images/image/download/**").permitAll()

						// Everything else requires JWT authentication
						.anyRequest().authenticated())

				// Authentication provider
				.authenticationProvider(authenticationProvider())

				// JWT filter
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}