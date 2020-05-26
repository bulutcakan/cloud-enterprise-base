package com.cloud.base.service;

import com.cloud.base.dto.JwtResponseDTO;
import com.cloud.base.dto.PasswordDTO;
import com.cloud.base.dto.UserDTO;

public interface SecurityUserService {

    String resetPassword(String email);

    String sendActivationCode(String email);

    JwtResponseDTO authenticateUser(UserDTO userDTO);

    String changePassword(PasswordDTO passwordDTO);

    String activateUser(String token);
}
