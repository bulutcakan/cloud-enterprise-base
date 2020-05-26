package com.cloud.base.dto;

import com.cloud.base.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordDTO implements Serializable {

    private String oldPassword;

    private String token;

    @ValidPassword
    private String newPassword;
}
