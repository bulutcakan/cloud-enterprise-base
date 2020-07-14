package com.cloud.base.dto;

import com.cloud.base.constans.marker.OnCreate;
import com.cloud.base.models.Education;
import com.cloud.base.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 2, max = 20)
    private String surname;

    @NotNull
    private Date birthDate;

    @NotBlank
    @Size(min = 1, max = 20)
    private String phoneNumber;

    @Max(10000)
    @Min(1)
    private Long feePerHour;

    @NotBlank
    @Size(min = 6, max = 50)
    @Email
    private String email;

    @NotNull
    private Long identity;

    @ValidPassword(groups = OnCreate.class)
    private String password;

    @ValidPassword(groups = OnCreate.class)
    private String rePassword;

    private String about;

    private Boolean active;

    private Set<RoleDTO> role;

    private Long specialityId;

    private Education education;

    private Long educationId;

    private List<UploadedFilesDTO> files;

    private SpecialtiesDTO specialtiesDTO;
}
