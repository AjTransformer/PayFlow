package com.example.main.entity;

import org.springframework.http.HttpStatusCode;

public class error_response {
    private HttpStatusCode statusCode;
    private String message;
    private long timestamp;
    private String stackTrace;

    public error_response(HttpStatusCode statusCode, String message, long timestamp, String stackTrace) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
        this.stackTrace = stackTrace;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
