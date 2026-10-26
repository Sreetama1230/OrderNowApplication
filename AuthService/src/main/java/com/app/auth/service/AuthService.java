package com.app.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.auth.customuserdetails.CustomUserDetails;
import com.app.auth.customuserdetails.CustomUserDetailsService;
import com.app.auth.dao.UserDao;
import com.app.auth.dto.AuthRequest;
import com.app.auth.entities.User;
import com.app.auth.exception.UserCreationError;
import com.app.auth.jwt.JwtUtils;

@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
			CustomUserDetailsService customUserDetailsService, UserDao userDao, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;

	}

	public User register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			return userDao.save(user);
		}catch(Exception e) {
			throw new UserCreationError(e.getMessage());
		}
	
	}

	public String login(AuthRequest authRequest) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		
		// 1. Get user details from authentication
		CustomUserDetails customUserDetails1 = (CustomUserDetails) auth.getPrincipal();

		// 2. Return success response (JWT or session or plb.title ain user data)
		String token = jwtUtils.generateToken(customUserDetails1);

		return token;

	}

}
