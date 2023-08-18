package com.example.xyz.exceptions;

public class ImpossibleOperationException extends RuntimeException {
    public ImpossibleOperationException(ApiErrors error) {
        super(String.valueOf(error));
    }
}
