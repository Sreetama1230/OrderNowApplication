package com.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.auth.dto.AuthRequest;
import com.app.auth.entities.User;
import com.app.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/register")
	public ResponseEntity< User> register(@RequestBody User user) {
		return new ResponseEntity<User>( authService.register(user) , HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequest authRequest){
		return new ResponseEntity<String>(authService.login(authRequest) , HttpStatus.OK);
	}
}
