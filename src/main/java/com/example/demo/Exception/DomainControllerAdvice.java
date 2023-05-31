package com.example.demo.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/*
컨트롤러 전역에서 발생하는
에러를 처리하는 handler
 */
@Slf4j
@ControllerAdvice
public class DomainControllerAdvice {
    @ExceptionHandler({DomainException.class})
    public ResponseEntity<ErrorResponseEntity> throwDomainExceptionResponse(DomainException exception){
        return ResponseEntity.badRequest()
                .body(ErrorResponseEntity.builder()
                        .code(exception.getCode())
                        .message(exception.getMessage())
                        .build());
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponseEntity> throwRuntimeException(RuntimeException exception) {

        log.error("{}", exception);
        return ResponseEntity.internalServerError()
                .body(ErrorResponseEntity.builder()
                        .code(1)
                        .message("서버에서 알 수 없는 에러가 발생했습니다.")
                        .build());

    }
}
