package com.mongle.api.exception;

import com.mongle.api.response.code.status.ErrorStatus;

public class UserNotLoggedInException extends GeneralException {

    public UserNotLoggedInException() {
        super(ErrorStatus.USER_NOT_FOUND);
    }
}
