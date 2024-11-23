package com.mongle.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mongle.api.domain.WinHistory;

public interface WinHistoryRepository extends JpaRepository<WinHistory, Integer> {

    
}
