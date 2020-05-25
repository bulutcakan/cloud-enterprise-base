package com.cloud.base.response;

import com.cloud.base.exception.ErrorCode;
import com.cloud.base.exception.ErrorCodeAware;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;

@JsonPropertyOrder({"code", "name", "description", "message", "trace", "clientMessage"})
@Getter
@Setter
public class ResponseError {
    private int code;
    private String name;
    private String description;
    private String message;
    private String trace;
    private String clientMessage;

    public ResponseError() {
    }

    public ResponseError(String message, String description) {
        this.description = description;
        this.message = message;

    }

    public ResponseError(Throwable cause, boolean withStackTrace) {
        ErrorCode errorCode;

        if (cause instanceof ErrorCodeAware) {
            errorCode = ((ErrorCodeAware) cause).getError();
        } else {
            errorCode = ErrorCode.General.GENERIC_ERROR;
        }

        this.code = errorCode.getCode();
        this.name = errorCode.getSection() + "." + errorCode.getName();
        this.description = errorCode.getDescription();
        this.message = cause.getMessage();

        if (withStackTrace) {
            this.trace = ExceptionUtils.getStackTrace(cause);
        }
    }


}
