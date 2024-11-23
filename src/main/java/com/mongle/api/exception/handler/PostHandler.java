package com.mongle.api.exception.handler;

import com.mongle.api.exception.GeneralException;
import com.mongle.api.response.code.BaseErrorCode;

public class PostHandler extends GeneralException {

    public PostHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
