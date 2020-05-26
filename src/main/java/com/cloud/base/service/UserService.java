package com.cloud.base.service;

import com.cloud.base.dto.Mail;
import com.cloud.base.dto.request.SignupRequest;

public interface UserService {

    String createUser(SignupRequest signupRequest);


}
