package com.mongle.api.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDTO {

    @Getter
    public static class CreateDTO {
        @NotBlank
        String title;
        @NotBlank
        String content;
        String imageUrl;
        Boolean isQuest;
        Integer questId = 1; // Default value
        @NotBlank
        String hashtagName; // Add this field to capture hashtag input

        public CreateDTO() {
            this.questId = 1; // Default value
        }
    }

    @Getter
    public static class UpdateDTO {
        @NotBlank
        String title;
        @NotBlank
        String content;
        String imageUrl;
        @NotBlank
        String hashtagName;
    }
}