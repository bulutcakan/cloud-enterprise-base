package com.cloud.base.controllers;

import com.cloud.base.dto.PasswordDTO;
import com.cloud.base.dto.request.LoginRequest;
import com.cloud.base.dto.request.SignupRequest;
import com.cloud.base.response.Response;
import com.cloud.base.service.SecurityUserService;
import com.cloud.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	SecurityUserService securityUserService;

	@PostMapping("/signin")
	public Response authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return new Response(securityUserService.authenticateUser(loginRequest));
	}

	@PostMapping("/signup")
	public Response registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return new Response(userService.createUser(signUpRequest));
	}

	@PostMapping("/resetPassword")
	public Response resetPassword(@Valid @Email @NotEmpty @RequestParam("email") String userEmail) {
		return new Response(securityUserService.resetPassword(userEmail));
	}

	@PostMapping("/changePassword")
	public Response changePassword(@RequestBody @Valid PasswordDTO passwordDTO) {
		return new Response(securityUserService.changePassword(passwordDTO));
	}
}
