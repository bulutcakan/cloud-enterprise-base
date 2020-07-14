package com.cloud.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadedFilesDTO implements Serializable {

    private Long id;

    private String URL;

    private Long fileTypeId;
}
