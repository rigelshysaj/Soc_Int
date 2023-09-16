package org.example.Repository;

import org.example.Entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
}
