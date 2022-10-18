package com.epam.esm.exceptions;

import com.epam.esm.exception.IncorrectParameterException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND);
        String message = "Requested resource not found (id=" + request.getParameter("id") + ")";
        int statusCode = HttpStatus.NOT_FOUND.value();
        errorResponse.setStatusCode(statusCode);
        errorResponse.setMessage(message);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        String message = e.getLocalizedMessage();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        errorResponse.setStatusCode(statusCode);
        errorResponse.setMessage(message);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public ResponseEntity<Object> handleIncorrectParameterException(IncorrectParameterException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        String message = e.getLocalizedMessage();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        errorResponse.setStatusCode(statusCode);
        errorResponse.setMessage(message);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        String message = e.getLocalizedMessage();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        errorResponse.setStatusCode(statusCode);
        errorResponse.setMessage(message);
        return buildResponseEntity(errorResponse);
    }

    public ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}