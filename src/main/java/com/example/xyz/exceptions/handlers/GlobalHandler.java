package com.example.xyz.exceptions.handlers;

import com.example.xyz.exceptions.ImpossibleOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(ImpossibleOperationException.class)
    public void handle(Exception ex) {
        log.info("Impossible operation exception occurred: {}", ex.getMessage());
    }
}
