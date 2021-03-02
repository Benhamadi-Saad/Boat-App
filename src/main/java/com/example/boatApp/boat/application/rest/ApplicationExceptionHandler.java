package com.example.boatApp.boat.application.rest;

import com.example.boatApp.boat.application.dto.ErrorResponse;
import com.example.boatApp.boat.domain.exception.BoatDomainException;
import com.example.boatApp.boat.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse resourceNotFoundException(ResourceNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({BoatDomainException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse boatDomainException(BoatDomainException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {

        var messgaes = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ErrorResponse(messgaes);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse InternalnException(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

}
