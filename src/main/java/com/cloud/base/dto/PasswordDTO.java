package com.cloud.base.dto;

import com.cloud.base.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PasswordDTO implements Serializable {

    private String oldPassword;

    private String token;

    @ValidPassword
    private String newPassword;
}
