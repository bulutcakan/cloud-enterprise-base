package com.cloud.base.dto;

import com.cloud.base.constans.marker.OnCreate;
import com.cloud.base.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 50)
    @Email
    private String email;

    @ValidPassword(groups = OnCreate.class)
    private String password;

    private Boolean active;

    private Set<RoleDTO> role;
}
