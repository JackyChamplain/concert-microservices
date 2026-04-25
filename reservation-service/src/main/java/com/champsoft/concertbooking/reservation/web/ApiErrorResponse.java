package com.champsoft.concertbooking.reservation.web;
import java.time.LocalDateTime;

public record ApiErrorResponse(String message, int status, LocalDateTime timestamp, String path) {}