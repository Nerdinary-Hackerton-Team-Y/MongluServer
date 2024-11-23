package com.mongle.api.dto.quest;

import java.util.List;

import com.mongle.api.domain.WinHistory;
import com.mongle.api.dto.post.PostResponseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestDetermineResultDto {

    private QuestResponseDto quest;
    private PostResponseDto first;
    private PostResponseDto second;
    private PostResponseDto third;

    public static QuestDetermineResultDto of(List<WinHistory> historyList) {
        var builder = QuestDetermineResultDto.builder()
            .quest(QuestResponseDto.of(historyList.get(0).getQuest()))
            .first(PostResponseDto.of(historyList.get(0).getPost()));

        if (historyList.size() >= 2) {
            builder = builder
                .second(PostResponseDto.of(historyList.get(1).getPost()));
        }

        if (historyList.size() >= 3) {
            builder = builder
                .third(PostResponseDto.of(historyList.get(2).getPost()));
        }

        return builder.build();
    }
}
