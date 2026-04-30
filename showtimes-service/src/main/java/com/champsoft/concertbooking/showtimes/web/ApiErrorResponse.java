package com.champsoft.concertbooking.showtimes.web;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;

    public ApiErrorResponse(String message, int status, LocalDateTime timestamp, String path) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
    }

    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
}