package com.mongle.api.dto.user;

import com.mongle.api.domain.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private String username;
    private String nickname;
    private String profilePictureUrl;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }
}
