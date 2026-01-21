package com.minibackoffice.backend.global.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) ResponseStatusException (401/403/404/409 등)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
            ResponseStatusException e,
            HttpServletRequest request
    ) {
        int status = e.getStatusCode().value();
        String code = e.getStatusCode().toString(); // "403 FORBIDDEN" 이런 형태라서 아래처럼 깔끔하게 바꿀 수도 있음

        // code를 더 깔끔하게: FORBIDDEN / UNAUTHORIZED 등만 쓰고 싶으면 이 줄로 교체:
        code = HttpStatus.valueOf(status).name();

        ApiErrorResponse body = ApiErrorResponse.of(
                status,
                code,
                e.getReason() != null ? e.getReason() : "요청 처리 중 오류가 발생했습니다.",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    // 2) @Valid 실패 (400)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            Exception e,
            HttpServletRequest request
    ) {
        var bindingResult = (e instanceof MethodArgumentNotValidException manv)
                ? manv.getBindingResult()
                : ((BindException) e).getBindingResult();

        List<ApiErrorResponse.FieldError> errors = bindingResult.getFieldErrors().stream()
                .map(fe -> new ApiErrorResponse.FieldError(
                        fe.getField(),
                        fe.getDefaultMessage()
                ))
                .toList();

        ApiErrorResponse body = ApiErrorResponse.of(
                400,
                "VALIDATION_ERROR",
                "요청 값이 올바르지 않습니다.",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.status(400).body(body);
    }

    // 3) 나머지 예상치 못한 에러(500) - 안전망
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception e,
            HttpServletRequest request
    ) {
        ApiErrorResponse body = ApiErrorResponse.of(
                500,
                "INTERNAL_SERVER_ERROR",
                "서버 오류가 발생했습니다.",
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(body);
    }
}
