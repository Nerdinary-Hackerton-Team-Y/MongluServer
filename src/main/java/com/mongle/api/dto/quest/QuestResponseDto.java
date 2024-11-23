package com.mongle.api.dto.quest;

import java.util.List;

import com.mongle.api.domain.Quest;
import com.mongle.api.dto.post.PostResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestResponseDto {

    private Integer id;
    private String title;
    private List<PostResponseDto> posts;
    
    public static QuestResponseDto of(Quest quest) {
        return QuestResponseDto.builder()
                .id(quest.getId())
                .title(quest.getTitle())
                .posts(quest.getPostList().stream()
                    .map(PostResponseDto::of)
                    .toList())
                .build();
    }
}
