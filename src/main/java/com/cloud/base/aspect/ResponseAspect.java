package com.cloud.base.aspect;


import com.cloud.base.response.Response;
import com.cloud.base.response.ResponseError;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;

@Aspect
@Component("responseAspect")
@Order(1) // IMPORTANT! ResponseAspect should always be the first
public class ResponseAspect {

    private static final Logger log = LoggerFactory.getLogger(ResponseAspect.class);

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;


    // -----

    @Around("inControllerLayer() && !isCustomResponse()")
    public Object prepareResponse(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return perform(proceedingJoinPoint, false);
    }

    @Around("inControllerLayer() && isCustomResponse()")
    public Object prepareResponseForCustomResponse(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return perform(proceedingJoinPoint, true);
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void inControllerLayer() {
    }

    @Pointcut("@annotation(com.cloud.base.annotations.CustomResponse)")
    public void isCustomResponse() {
    }

    private Object perform(ProceedingJoinPoint proceedingJoinPoint, boolean isCustomResponse) throws Throwable {
        try {
            Object retVal = proceedingJoinPoint.proceed();

            if (!isCustomResponse) {
                Response response = (Response) retVal;
                return response;
            } else {
                return retVal;
            }
        } catch (Throwable t) {

            log.error("Exception {} caught, method : {}, message : {}, trace : {}",
                    t.getClass().getCanonicalName(),
                    getMethod(proceedingJoinPoint).getName(),
                    t.getMessage(),
                    ExceptionUtils.getStackTrace(t));

            if (!isCustomResponse && !isBadRequestException(t)) {
                return prepareErrorResponse(t);
            } else {
                throw t;
            }
        }
    }


    private static Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod();
    }

    private Response prepareErrorResponse(Throwable cause) {
        Response response = new Response(cause);
        response.setSuccess(false);

        ResponseError responseError = ((null != activeProfile) && (!activeProfile.equals("prod"))) ?
                new ResponseError(cause, true) : new ResponseError(cause, false);

        response.setError(responseError);

        return response;
    }

    private boolean isBadRequestException(Throwable t) {

        if (t instanceof ConstraintViolationException || t instanceof MethodArgumentNotValidException) {
            return true;
        }

        return false;
    }
}
