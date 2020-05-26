package com.cloud.base.service;

import com.cloud.base.dto.PasswordDTO;
import com.cloud.base.dto.request.LoginRequest;
import com.cloud.base.dto.response.JwtResponse;

public interface SecurityUserService {

    String resetPassword(String email);

    JwtResponse authenticateUser(LoginRequest loginRequest);

    String changePassword(PasswordDTO passwordDTO);
}
