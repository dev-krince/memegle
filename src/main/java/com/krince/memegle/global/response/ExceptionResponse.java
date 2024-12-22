package com.krince.memegle.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "예외 응답 공통 Response")
public class ExceptionResponse extends GlobalResponse {

    public ExceptionResponse(ExceptionResponseCode responseCode) {
        super(responseCode);
    }

    public ExceptionResponse(ExceptionResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
