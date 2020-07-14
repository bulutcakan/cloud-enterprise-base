package com.cloud.base.exception;

import com.cloud.base.exception.base.CloudBaseException;
import com.cloud.base.exception.code.ErrorCode;

public class SpecialtiesException extends CloudBaseException {
    public SpecialtiesException(String message) {
        super(message);
    }

    public SpecialtiesException(ErrorCode code, String message) {
        super(code, message);
    }

    public SpecialtiesException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
