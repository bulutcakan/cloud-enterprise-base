package com.cloud.base.exception.base;

public class JSONException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JSONException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
