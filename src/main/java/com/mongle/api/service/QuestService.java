package com.mongle.api.service;

import com.mongle.api.dto.quest.QuestResponseDto;

public interface QuestService {

    QuestResponseDto getCurrentQuest();
    void determineWinner();

}
