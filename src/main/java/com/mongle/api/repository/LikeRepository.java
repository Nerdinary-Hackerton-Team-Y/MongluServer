package com.mongle.api.repository;

import com.mongle.api.domain.LikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeHistory, Long> {
}
