package org.example.Repository;

import org.example.Entity.EventEntity;
import org.example.Entity.UserEntity;
import org.example.Enum.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query("SELECT e FROM EventEntity e " +
            "WHERE e.eventStartDateTime >= :startTime " +
            "AND e.eventDeadline <= :endTime")
    List<EventEntity> findByStartTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT e FROM EventEntity e " +
            "WHERE e.host = :user " +
            "AND e.eventStartDateTime >= :startTime " +
            "AND e.eventStartDateTime <= :endTime")
    List<EventEntity> findByUserAndStartTimeBetween(@Param("user") UserEntity user, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT e FROM EventEntity e " +
            "WHERE e.category = :category " +
            "AND e.eventStartDateTime >= :startTime " +
            "AND e.eventStartDateTime <= :endTime")
    List<EventEntity> findByCategoryAndStartTimeBetween(@Param("category") EventCategory category, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT e FROM EventEntity e " +
            "WHERE e.host = :user " +
            "AND e.category = :category " +
            "AND e.eventStartDateTime >= :startTime " +
            "AND e.eventStartDateTime <= :endTime")
    List<EventEntity> findByUserAndCategoryAndStartTimeBetween(
            @Param("user") UserEntity user,
            @Param("category") EventCategory category,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    /*
    @Query("SELECT e FROM EventEntity e WHERE e.startTime BETWEEN ?1 AND ?2 AND e.category = ?3")
    List<EventEntity> findEventsByTimeRangeAndCategory(LocalDateTime startTime, LocalDateTime endTime, String category);
     */
}