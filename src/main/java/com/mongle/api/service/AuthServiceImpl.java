package com.mongle.api.service;

import org.springframework.stereotype.Service;

import com.mongle.api.domain.User;
import com.mongle.api.dto.auth.RegisterDto;

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
    public User getUserFronToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserFronToken'");
    }

    
}
