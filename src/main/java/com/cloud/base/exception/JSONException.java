package com.cloud.base.exception;

public class JSONException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JSONException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
