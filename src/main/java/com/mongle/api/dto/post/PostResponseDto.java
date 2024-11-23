package com.mongle.api.dto.post;

import java.time.LocalDateTime;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
public class PostResponseDto {

    private Integer id;
    private String imageUrl;
    private String nickname;
    private String title;
    private String content;
    private Boolean isQuest;
    private Integer score;
    private Integer likeCount;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .imageUrl(post.getImageUrl())
                .title(post.getTitle())
                .content(post.getContent())
                .isQuest(post.getIsQuest())
                .score(post.getScore())
                .likeCount(post.getLikeList().size())
                .build();
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDto {
        Integer postId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResultDto {
        Integer postId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteResultDto {
        Integer postId;
        Status status;
        LocalDateTime deletedAt;
    }
}

