package com.cloud.base.controllers;

import com.cloud.base.response.Response;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public Response allAccess() {
		return new Response("Public Content.");
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Response userAccess() {
		return new Response("User Content.");
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public Response moderatorAccess() {
		return new Response("Moderator Board.");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public Response adminAccess() {
		return new Response("Admin Board.");
	}
}
