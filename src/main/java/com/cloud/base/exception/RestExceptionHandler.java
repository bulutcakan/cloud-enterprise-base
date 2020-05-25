package com.cloud.base.exception;


import com.cloud.base.response.Response;
import com.cloud.base.response.ResponseError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class RestExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Response onConstraintValidationException(ConstraintViolationException e) {

        Response response = new Response();

        List<String> errors = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), String.join(",", errors));
        responseError.setCode(HttpStatus.BAD_REQUEST.value());
        response.setError(responseError);

        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Response onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Response response = new Response();

        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), String.join(",", errors));
        responseError.setCode(HttpStatus.BAD_REQUEST.value());
        response.setError(responseError);

        return response;
    }
}
