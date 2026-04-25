package com.champsoft.concertbooking.showtimes.web;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {}