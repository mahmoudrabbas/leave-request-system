package com.empSystem.shared;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class GlobalResponse<T> {
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private final LocalDateTime timestamp;
    private final String status;
    private final HttpStatus statusCode;
    private final T data;
    private final Map<String, String> error;

    public GlobalResponse(Map<String, String> errors, HttpStatus statusCode) {
        this.error = errors;
        this.timestamp = LocalDateTime.now();
        this.data = null;
        this.statusCode = statusCode;
        this.status = GlobalResponse.ERROR;
    }

    public GlobalResponse(T data, HttpStatus statusCode) {
        this.error = null;
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.statusCode = statusCode;
        this.status = GlobalResponse.SUCCESS;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    public Map<String, String> getError() {
        return error;
    }
}


