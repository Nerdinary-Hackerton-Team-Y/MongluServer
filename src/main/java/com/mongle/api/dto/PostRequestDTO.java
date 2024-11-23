package com.mongle.api.dto;

import lombok.Getter;

@Getter
public class PostRequestDTO {

    @Getter
    public static class CreateDto {
        Integer userId; // JWT 쓰는 거면 지워도 되나요?
        String content;
        String imageUrl;
        Boolean isQuest;
    }
}
