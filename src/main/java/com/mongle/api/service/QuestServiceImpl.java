package com.mongle.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.Quest;
import com.mongle.api.domain.WinHistory;
import com.mongle.api.domain.enums.Status;
import com.mongle.api.dto.quest.QuestDetermineResultDto;
import com.mongle.api.dto.quest.QuestResponseDto;
import com.mongle.api.repository.QuestRepository;
import com.mongle.api.repository.WinHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestServiceImpl implements QuestService {

    private final QuestRepository questRepository;
    private final WinHistoryRepository winHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public QuestResponseDto getCurrentQuest() {
        Quest quest = getCurrent();
        Collections.sort(quest.getPostList(), (p1, p2) -> p2.getScore() - p1.getScore());

        return QuestResponseDto.of(quest);
    }

    @Override
    public QuestDetermineResultDto determineWinner() {
        Quest quest = getCurrent();
        List<Post> posts = quest.getPostList();
        Collections.sort(posts, (p1, p2) -> p2.getScore() - p1.getScore());
        
        List<WinHistory> list = new ArrayList<>();
        for (int score = 1; score <= Math.min(3, posts.size()); score++) {
            WinHistory winHistory = WinHistory.builder()
                    .score(score)
                    .post(posts.get(score - 1))
                    .quest(quest)
                    .user(posts.get(score - 1).getUser())
                    .build();
            list.add(winHistory);
            winHistoryRepository.save(winHistory);
        }

        return QuestDetermineResultDto.of(list);
    }

    private Quest getCurrent() {
        List<Quest> quests = questRepository.findByStatus(Status.ACTIVATED);

        if (quests.size() != 1) {
            throw new RuntimeException();
        }

        return quests.get(0);
    }

}
