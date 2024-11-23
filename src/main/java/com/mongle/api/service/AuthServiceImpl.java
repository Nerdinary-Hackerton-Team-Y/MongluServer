package com.mongle.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mongle.api.domain.User;
import com.mongle.api.dto.auth.RegisterDto;
import com.mongle.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.secret}")
    private String secret;

    private final UserRepository UserRepository;
    private final RandomNicknameService randomNicknameService;

    private static final String JWT_ISSUER = "mongle";
    private static int nicknameSeed = 0;

    @Override
    public void register(RegisterDto dto) {
        String randomNickname = randomNicknameService.getRandomNickname(nicknameSeed++);
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .profilePictureUrl(dto.getProfilePictureUrl())
                .nickname(randomNickname)
                .build();
        UserRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public String login(String username, String password) {
        User user = UserRepository.findByUsername(username)
                .orElseThrow();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            String token = JWT.create()
                .withIssuer(JWT_ISSUER)
                .withSubject(user.getUsername())
                .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserFronToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(JWT_ISSUER)
                .build();

            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw new RuntimeException();
        }

        String username = decodedJWT.getSubject();
        return UserRepository.findByUsername(username)
                .orElseThrow();
    }

}
