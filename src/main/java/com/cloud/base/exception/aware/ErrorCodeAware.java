package com.cloud.base.exception.aware;

import com.cloud.base.exception.code.ErrorCode;

public interface ErrorCodeAware {
    ErrorCode getError();
}
