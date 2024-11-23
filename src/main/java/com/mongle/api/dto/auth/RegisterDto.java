package com.mongle.api.dto.auth;

import lombok.Data;

@Data
public class RegisterDto {

    private final String username;
    private final String password;
    private final String profilePictureUrl;

}
