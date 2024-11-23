package com.mongle.api.domain.dto;

import com.mongle.api.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public CommentResDto(Comment comment) {
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updateAt = comment.getUpdatedAt();
    }
}
