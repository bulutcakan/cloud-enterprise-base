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

    @Section(min = 100, max = 1000)
    public interface Session {
        ErrorCode SESSION_NOT_FOUND = new ErrorCode(100);
        ErrorCode INVALID_ACCESS = new ErrorCode(101);
        ErrorCode USER_SESSION_NOT_FOUND = new ErrorCode(102);
    }



    @Section(min = 2000, max = 3000)
    public interface Role {
        ErrorCode INVALID_ROLE = new ErrorCode(2000);
        ErrorCode ROLE_NOT_EXIST = new ErrorCode(2001, "Role not found!");
        ErrorCode ROLE_NAME_ALREADY_EXIST = new ErrorCode(2002, "Role name already exist!");
        ErrorCode ROLE_NOT_SAVED = new ErrorCode(2003);
        ErrorCode ROLE_NOT_UPDATED = new ErrorCode(2004);
        ErrorCode ROLE_NOT_DELETED = new ErrorCode(2005);
    }

    @Section(min = 3000, max = 4000)
    public interface Application {
        ErrorCode INVALID_APPLICATION = new ErrorCode(3000);
        ErrorCode APPLICATION_NOT_EXIST = new ErrorCode(3001, "Application not found!");
        ErrorCode APPLICATION_NOT_SAVED = new ErrorCode(3002);
        ErrorCode APPLICATION_NOT_UPDATED = new ErrorCode(3003);
        ErrorCode APPLICATION_NOT_DELETED = new ErrorCode(3004);

    }

    @Section(min = 4000, max = 5000)
    public interface Service {
        ErrorCode INVALID_SERVICE = new ErrorCode(4000);
        ErrorCode SERVICE_NOT_EXIST = new ErrorCode(4001, "Service not found!");
        ErrorCode SERVICE_NOT_DELETED = new ErrorCode(4002);
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
