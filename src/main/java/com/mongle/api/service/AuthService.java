package com.mongle.api.service;

import com.mongle.api.domain.User;
import com.mongle.api.dto.auth.RegisterDto;

public interface AuthService {

    void register(RegisterDto dto);
    String login(String username, String password);
    User getUserFronToken(String token);

}
