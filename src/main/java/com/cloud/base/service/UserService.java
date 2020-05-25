package com.cloud.base.service;

import com.cloud.base.dto.request.LoginRequest;
import com.cloud.base.dto.request.SignupRequest;
import com.cloud.base.dto.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    String createUser(SignupRequest signupRequest);

    JwtResponse authenticateUser(LoginRequest loginRequest);
}
