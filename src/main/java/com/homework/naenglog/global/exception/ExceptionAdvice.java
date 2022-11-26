package com.homework.naenglog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @Getter
    @AllArgsConstructor
    @Builder
    private static class ErrorDto {
        private int status;
        private String msg;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDto> handleException(BusinessException ex) {
        return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getStatus().value(), ex.getMessage()), ex.getStatus());
    }

}
