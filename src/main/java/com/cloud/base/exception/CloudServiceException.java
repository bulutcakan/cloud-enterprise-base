package com.cloud.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CloudServiceException extends RuntimeException {
    public CloudServiceException(String message) {
        super(message);
    }

    public CloudServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
