package com.example.springBootIA.responses;



import com.example.springBootIA.exceptions.ErrorCode;

import java.util.UUID;

public record ErrorResponse(
        String message,
        Integer code,
        UUID correlationId,
        String... params
) {

    public static ErrorResponse of(ErrorCode errorCode, UUID correlationId, String... params) {
        return new ErrorResponse(errorCode.message(), errorCode.code(), correlationId, params);
    }
}