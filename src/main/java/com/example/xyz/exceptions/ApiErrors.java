package com.example.xyz.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiErrors {

    ENCODING_ERROR("Error occurred during encoding", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("User not found in db", HttpStatus.NOT_FOUND),
    USER_IS_DISABLED("User is disabled", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("User password mismatch", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED("Token expired", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED),
    ITEM_NOT_FOUND("Item not found", HttpStatus.NOT_FOUND),
    UNEXPECTED_VERSION("Unexpected version", HttpStatus.BAD_REQUEST),
    TAG_NOT_FOUND("Tag not found", HttpStatus.NOT_FOUND);

    private final String data;
    private final HttpStatus httpStatus;
}
