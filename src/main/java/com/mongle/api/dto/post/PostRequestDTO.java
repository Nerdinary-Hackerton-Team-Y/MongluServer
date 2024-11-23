package com.mongle.api.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

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
        @NotNull
        List<String> hashtags; // Change to list of strings

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
        @NotNull
        List<String> hashtags; // Change to list of strings
    }
}