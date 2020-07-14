package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class EducationException extends CloudBaseException {
    public EducationException(String message) {
        super(message);
    }

    public EducationException(ErrorCode code, String message) {
        super(code, message);
    }

    public EducationException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
