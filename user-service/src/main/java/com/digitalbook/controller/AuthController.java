package com.digitalbook.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.payload.request.LoginRequest;
import com.digitalbook.payload.request.SignupRequest;
import com.digitalbook.payload.response.JwtResponse;
import com.digitalbook.payload.response.MessageResponse;
import com.digitalbook.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		logger.info("   authenticateUser() ");

		ResponseEntity<JwtResponse> response = authService.authenticateUser(loginRequest);

		return response;
	}

	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		logger.info("  registerUser() " + signUpRequest.getRole());

		ResponseEntity<MessageResponse> response = authService.registerUser(signUpRequest);

		return response;
	}

}
