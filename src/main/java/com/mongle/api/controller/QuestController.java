package com.mongle.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongle.api.dto.quest.QuestDetermineResultDto;
import com.mongle.api.dto.quest.QuestResponseDto;
import com.mongle.api.service.QuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quest")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    @GetMapping("/current")
    public QuestResponseDto getCurrentQuest() {
        return questService.getCurrentQuest();
    }

    @PostMapping("/determine")
    public QuestDetermineResultDto determineQuest() {
        return questService.determineWinner();
    }

}
