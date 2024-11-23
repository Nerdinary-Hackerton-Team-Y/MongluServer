package com.mongle.api.service;

import com.mongle.api.dto.quest.QuestDetermineResultDto;
import com.mongle.api.dto.quest.QuestResponseDto;

public interface QuestService {

    QuestResponseDto getCurrentQuest();
    QuestDetermineResultDto determineWinner();

}
