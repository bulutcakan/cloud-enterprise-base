package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class UploadException extends CloudBaseException {
    public UploadException(String message) {
        super(message);
    }

    public UploadException(ErrorCode code, String message) {
        super(code, message);
    }

    public UploadException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
