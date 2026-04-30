package com.champsoft.concertbooking.concerts.web;

import com.champsoft.concertbooking.concerts.application.exception.ConcertAlreadyExistsException;
import com.champsoft.concertbooking.concerts.application.exception.ConcertNotFoundException;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidConcertNameException;
import com.champsoft.concertbooking.concerts.domain.exception.InvalidVenueNameException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //DOMAIN
    @ExceptionHandler({InvalidConcertNameException.class, InvalidVenueNameException.class})
    public ResponseEntity<ApiErrorResponse> handleDomainExceptions(RuntimeException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    //APPLICATION
    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ConcertNotFoundException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(ConcertAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(ConcertAlreadyExistsException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(String message, HttpStatus status, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                message,
                status.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, status);
    }
}