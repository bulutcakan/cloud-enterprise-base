package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class FileTypeException extends CloudBaseException {
    public FileTypeException(String message) {
        super(message);
    }

    public FileTypeException(ErrorCode code, String message) {
        super(code, message);
    }

    public FileTypeException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
