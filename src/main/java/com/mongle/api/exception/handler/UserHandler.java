package com.mongle.api.exception.handler;

import com.mongle.api.exception.GeneralException;
import com.mongle.api.response.code.BaseErrorCode;

public class UserHandler extends GeneralException {

        public UserHandler(BaseErrorCode errorCode) {
            super(errorCode);
        }

}
