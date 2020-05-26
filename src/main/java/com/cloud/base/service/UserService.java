package com.cloud.base.service;

import com.cloud.base.dto.request.LoginRequest;
import com.cloud.base.dto.request.SignupRequest;
import com.cloud.base.dto.response.JwtResponse;

public interface UserService {

    String createUser(SignupRequest signupRequest);


}
