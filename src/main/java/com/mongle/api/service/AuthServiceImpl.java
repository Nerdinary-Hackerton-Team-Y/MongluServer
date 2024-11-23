package com.mongle.api.service;

import org.springframework.stereotype.Service;

import com.mongle.api.domain.User;
import com.mongle.api.dto.auth.RegisterDto;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void register(RegisterDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public String login(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public User getUserFromToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserFronToken'");

    }

    @Override
    public String getTokenFromHeader(@RequestHeader("Authorization") String authorizationHeader) {
        // Split the header to get the token
        String token = authorizationHeader.split(" ")[1];
        return token;
    }
}
