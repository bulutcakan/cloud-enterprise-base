package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class UserException extends CloudBaseException {
    public UserException(String message) {
        super(message);
    }

    public UserException(ErrorCode code, String message) {
        super(code, message);
    }

    public UserException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
