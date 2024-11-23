package com.mongle.api.dto.post;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostRequestDto {

    @Getter
    public static class CreateDto {
        @NotBlank
        String title;
        @NotBlank
        String content;
        MultipartFile imageUrl;
        Boolean isQuest;
        Integer questId = 1; // Default value
        @NotNull
        List<String> hashtags; // Change to list of strings

        public CreateDto() {
            this.questId = 1; // Default value
        }
    }

    @Getter
    public static class UpdateDto {
        @NotBlank
        String title;
        @NotBlank
        String content;
        MultipartFile imageUrl;
        @NotNull
        List<String> hashtags; // Change to list of strings
    }
}

