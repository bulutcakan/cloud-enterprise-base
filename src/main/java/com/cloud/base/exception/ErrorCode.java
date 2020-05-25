package com.cloud.base.exception;

public final class ErrorCode extends AbstractErrorCode {
    private static final long serialVersionUID = 1L;

    private ErrorCode(int code) {
        super(code);
    }

    private ErrorCode(int code, String description) {
        super(code, description);
    }

    public static void initialize() {
        baseInitialize();
    }


    @Section(min = 0, max = 100)
    public interface General {
        ErrorCode GENERIC_ERROR = new ErrorCode(0);
        ErrorCode OPTIMISTIC_LOCK_FAILURE = new ErrorCode(1);
        ErrorCode ALREADY_EXIST = new ErrorCode(2);
        ErrorCode BAD_PARAMETER = new ErrorCode(3);
        ErrorCode MISSING_PARAMETER = new ErrorCode(4);
        ErrorCode ILLEGAL_HOST = new ErrorCode(5);
        ErrorCode SERVICE_UNAVAILABLE = new ErrorCode(99);
    }

    @Section(min = 5000, max = 6000)
    public interface User {
        ErrorCode INVALID_USER = new ErrorCode(5000);
        ErrorCode USER_NOT_EXIST = new ErrorCode(5001, "User not found!");
        ErrorCode USER_NOT_SAVED = new ErrorCode(5002);
        ErrorCode USER_NOT_UPDATED = new ErrorCode(5003);
        ErrorCode USER_NAME_ALREADY_EXIST = new ErrorCode(5004);
        ErrorCode USER_DOES_NOT_HAVE_PERMISSION = new ErrorCode(5005, "User does not have permission for this operation");
        ErrorCode PASSWORD_MISMATCH = new ErrorCode(5006, "Password does not match");
    }
    @Section(min = 8000, max = 9000)
    public interface Search {
        ErrorCode INVALID_SEARCH_PARAM = new ErrorCode(8000);
        ErrorCode NO_SEARCH_RESULT = new ErrorCode(8001);

    }
}
