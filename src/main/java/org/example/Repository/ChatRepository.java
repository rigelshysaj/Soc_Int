package org.example.Repository;

import org.example.Entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
}