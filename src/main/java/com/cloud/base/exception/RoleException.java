package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class RoleException extends CloudBaseException {

    public RoleException(String message) {
        super(message);
    }

    public RoleException(ErrorCode code, String message) {
        super(code, message);
    }

    public RoleException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
