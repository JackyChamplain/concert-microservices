package com.champsoft.concertbooking.showtimes.api;

import com.champsoft.concertbooking.showtimes.exception.ShowtimeAlreadyExistsException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeModificationNotAllowedException;
import com.champsoft.concertbooking.showtimes.domain.exception.InvalidShowtimeException;
import com.champsoft.concertbooking.showtimes.exception.ShowtimeNotFoundException;
import com.champsoft.concertbooking.showtimes.web.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ShowtimeExceptionHandler {

    @ExceptionHandler(ShowtimeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ShowtimeNotFoundException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidShowtimeException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalid(InvalidShowtimeException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ShowtimeAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(ShowtimeAlreadyExistsException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ShowtimeModificationNotAllowedException.class)
    public ResponseEntity<ApiErrorResponse> handleModificationNotAllowed(ShowtimeModificationNotAllowedException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception e, HttpServletRequest request) {
        return buildResponse("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
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