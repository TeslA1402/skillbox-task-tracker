package org.example.skillboxtasktracker.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(NotFoundException e) {
        return getErrorResponse(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception e) {
        log.error("Handle exception: {}", e.getClass().getName(), e);
        return new ErrorResponse("Unknown Exception");
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(WebExchangeBindException e) {
        return getErrorResponse(e);
    }

    private ErrorResponse getErrorResponse(Exception e) {
        log.error("Handle exception: {}", e.getClass().getName(), e);
        return new ErrorResponse(e.getMessage());
    }
}
