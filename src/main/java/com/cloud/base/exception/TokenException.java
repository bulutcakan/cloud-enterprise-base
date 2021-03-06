package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class TokenException extends CloudBaseException {
    public TokenException(String message) {
        super(message);
    }

    public TokenException(ErrorCode code, String message) {
        super(code, message);
    }

    public TokenException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
