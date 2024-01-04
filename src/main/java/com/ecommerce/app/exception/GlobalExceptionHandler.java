package com.ecommerce.app.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String ACCESS_DENIED_REASON = "access_denied_reason";
    public static final String AUTHORIZATION_FAILURE = "Authorization Failure";
    public static final String AUTHENTICATION_FAILURE = "Authentication Failure";

    @ExceptionHandler(value = {Exception.class})
    public ProblemDetail handleException(Exception e) {
        e.printStackTrace();
       ProblemDetail problemDetail;

        if (e instanceof BadCredentialsException) {
            problemDetail =
                    ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
            problemDetail.setProperty(ACCESS_DENIED_REASON, AUTHENTICATION_FAILURE);
            return problemDetail;
        } else if (
                e instanceof AccessDeniedException
                        || e instanceof SignatureException || e instanceof ExpiredJwtException
        ) {
            problemDetail =
                    ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
            problemDetail.setProperty(ACCESS_DENIED_REASON, AUTHORIZATION_FAILURE);
            return problemDetail;
        }
        else if (e instanceof RESTException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        else {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return problemDetail;
    }
}
