package com.cloud.base.dto;

import com.cloud.base.constans.MailType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mail implements Serializable {

    private String from;
    private String mailTo;
    private String subject;
    private MailType mailType;
    private List<Object> attachments;
    private Map<String, Object> props;
}
