package com.cloud.base.exception;

public abstract class CloudBaseException extends RuntimeException implements ErrorCodeAware {
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    public CloudBaseException(String message) {
        super(message);
        this.errorCode = ErrorCode.General.GENERIC_ERROR;
    }

    public CloudBaseException(ErrorCode code, String message) {
        super(message);
        this.errorCode = code;
    }

    public CloudBaseException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
    }

    @Override
    public ErrorCode getError() {
        return errorCode;
    }
}
