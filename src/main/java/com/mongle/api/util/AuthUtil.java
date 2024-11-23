package com.mongle.api.util;

import jakarta.servlet.http.HttpServletRequest;

import com.mongle.api.domain.User;
import com.mongle.api.service.AuthService;

public class AuthUtil {

    public static User getUserFromRequest(HttpServletRequest req, AuthService authService) {
        String token = req.getHeader("Authorization")
        .split(" ")[1];
        return authService.getUserFronToken(token);

    }
}
