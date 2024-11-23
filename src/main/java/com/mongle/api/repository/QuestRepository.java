package com.mongle.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mongle.api.domain.Quest;
import com.mongle.api.domain.enums.Status;

public interface QuestRepository extends JpaRepository<Quest, Integer> {

    List<Quest> findByStatus(Status status);
}
