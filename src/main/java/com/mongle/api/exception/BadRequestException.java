package com.mongle.api.exception;

import com.mongle.api.response.code.status.ErrorStatus;

public class BadRequestException extends GeneralException {

    public BadRequestException() {
        super(ErrorStatus._BAD_REQUEST);
    }
}
