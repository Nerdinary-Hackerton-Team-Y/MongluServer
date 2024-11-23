package com.mongle.api.dto;

import lombok.Getter;

@Getter
public class PostRequestDTO {

    @Getter
    public static class CreateDto {
        String content;
        String imageUrl;
        Boolean isQuest;
        Integer questId;
    }
}
