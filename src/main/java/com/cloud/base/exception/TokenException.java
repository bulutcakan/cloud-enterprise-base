package com.cloud.base.exception;

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
