package com.mongle.api.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongle.api.domain.Post;
import com.mongle.api.domain.Quest;
import com.mongle.api.domain.WinHistory;
import com.mongle.api.domain.enums.Status;
import com.mongle.api.dto.quest.QuestResponseDto;
import com.mongle.api.repository.QuestRepository;
import com.mongle.api.repository.UserRepository;
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
        return QuestResponseDto.of(getCurrent());
    }

    @Override
    public void determineWinner() {
        Quest quest = getCurrent();
        List<Post> posts = quest.getPostList();
        Collections.sort(posts, (p1, p2) -> p2.getRank() - p1.getRank());
        
        for (int rank = 1; rank <= Math.min(3, posts.size()); rank++) {
            WinHistory winHistory = WinHistory.builder()
                    .rank(rank)
                    .post(posts.get(rank - 1))
                    .quest(quest)
                    .user(posts.get(rank - 1).getUser())
                    .build();
            winHistoryRepository.save(winHistory);
        }
    }

    private Quest getCurrent() {
        List<Quest> quests = questRepository.findByStatus(Status.ACTIVATED);

        if (quests.size() != 1) {
            throw new RuntimeException();
        }

        return quests.get(0);
    }

}
