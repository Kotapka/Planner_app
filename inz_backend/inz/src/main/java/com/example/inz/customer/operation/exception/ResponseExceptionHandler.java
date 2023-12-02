package com.example.inz.customer.operation.exception;

import com.example.inz.customer.operation.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handlerException(UserNotFoundException e) {
        return ResponseEntity.status(e.getCode())
                .body(ErrorDto.builder().message(e.getMessage()).build());
    }
}
