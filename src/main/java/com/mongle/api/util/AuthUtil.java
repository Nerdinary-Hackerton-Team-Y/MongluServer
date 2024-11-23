package com.mongle.api.util;

import jakarta.servlet.http.HttpServletRequest;

import com.mongle.api.domain.User;
import com.mongle.api.exception.BadRequestException;
import com.mongle.api.service.AuthService;

public class AuthUtil {

    public static User getUserFromRequest(HttpServletRequest req, AuthService authService) {
        String header = req.getHeader("Authorization");
        if (header == null)
            throw new BadRequestException();

        String token = req.getHeader("Authorization")
        .split(" ")[1];
        return authService.getUserFronToken(token);

    }
}
