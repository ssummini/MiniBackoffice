package com.minibackoffice.backend.global.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) @Valid 검증 실패 (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiErrorResponse body = new ApiErrorResponse(
                400,
                "BAD_REQUEST",
                message,
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 2) ResponseStatusException (404, 401, 403, 409 등)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatus(
            ResponseStatusException e,
            HttpServletRequest request
    ) {
        int status = e.getStatusCode().value();
        String message = (e.getReason() == null || e.getReason().isBlank())
                ? "요청 처리 중 오류가 발생했습니다."
                : e.getReason();

        ApiErrorResponse body = new ApiErrorResponse(
                status,
                e.getStatusCode().toString(), // 예: "404 NOT_FOUND"
                message,
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(e.getStatusCode()).body(body);
    }
}
