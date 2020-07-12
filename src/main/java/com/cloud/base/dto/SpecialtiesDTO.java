package com.cloud.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialtiesDTO implements Serializable {

    Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    String name;

    String imageURL;

}
