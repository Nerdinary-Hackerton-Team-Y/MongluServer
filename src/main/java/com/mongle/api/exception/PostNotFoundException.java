package com.mongle.api.exception;

import com.mongle.api.response.code.status.ErrorStatus;

public class PostNotFoundException extends GeneralException {

    public PostNotFoundException() {
        super(ErrorStatus.POST_NOT_FOUND);
    }
}
