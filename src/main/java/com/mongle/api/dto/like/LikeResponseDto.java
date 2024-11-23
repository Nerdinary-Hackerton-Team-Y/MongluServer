package com.mongle.api.dto.like;

import com.mongle.api.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LikeResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDto {
        Long likeId;
        Integer PostId;
        Status status;
        LocalDateTime createdAt;
    }
}
