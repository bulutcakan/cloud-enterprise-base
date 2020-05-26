package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class SecurityException extends CloudBaseException {
    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(ErrorCode code, String message) {
        super(code, message);
    }

    public SecurityException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
