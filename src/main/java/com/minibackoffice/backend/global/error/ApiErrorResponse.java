package com.minibackoffice.backend.global.error;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse {

    private final int status;
    private final String code;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private final List<FieldError> errors; // validation용 (없으면 null)

    private ApiErrorResponse(int status, String code, String message, String path, List<FieldError> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public static ApiErrorResponse of(int status, String code, String message, String path) {
        return new ApiErrorResponse(status, code, message, path, null);
    }

    public static ApiErrorResponse of(int status, String code, String message, String path, List<FieldError> errors) {
        return new ApiErrorResponse(status, code, message, path, errors);
    }

    public int getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public List<FieldError> getErrors() { return errors; }

    public static class FieldError {
        private final String field;
        private final String reason;

        public FieldError(String field, String reason) {
            this.field = field;
            this.reason = reason;
        }

        public String getField() { return field; }
        public String getReason() { return reason; }
    }
}
