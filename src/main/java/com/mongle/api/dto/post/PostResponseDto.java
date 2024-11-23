package com.mongle.api.dto.post;

import com.mongle.api.domain.Post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseDto {

    private Integer id;
    private String imageUrl;
    private String title;
    private String content;
    private Boolean isQuest;
    private Integer rank;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .imageUrl(post.getImageUrl())
                .title(post.getTitle())
                .content(post.getContent())
                .isQuest(post.getIsQuest())
                .rank(post.getRank())
                .build();
    }
}
