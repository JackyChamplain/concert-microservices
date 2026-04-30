package com.champsoft.concertbooking.reservation.api;

import com.champsoft.concertbooking.reservation.application.exception.ReservationAlreadyExistsException;
import com.champsoft.concertbooking.reservation.application.exception.ReservationModificationNotAllowedException;
import com.champsoft.concertbooking.reservation.domain.exception.DuplicateReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.InvalidReservationException;
import com.champsoft.concertbooking.reservation.domain.exception.ReservationNotFoundException;
import com.champsoft.concertbooking.reservation.web.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ReservationNotFoundException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DuplicateReservationException.class, ReservationAlreadyExistsException.class})
    public ResponseEntity<ApiErrorResponse> handleConflict(RuntimeException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({InvalidReservationException.class, ReservationModificationNotAllowedException.class})
    public ResponseEntity<ApiErrorResponse> handleInvalidReservation(RuntimeException e, HttpServletRequest request) {
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