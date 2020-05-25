package com.cloud.base.controllers;

import com.cloud.base.dto.request.LoginRequest;
import com.cloud.base.dto.request.SignupRequest;
import com.cloud.base.response.Response;
import com.cloud.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@PostMapping("/signin")
	public Response authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return new Response(userService.authenticateUser(loginRequest));
	}

	@PostMapping("/signup")
	public Response registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return new Response(userService.createUser(signUpRequest));
	}
}
