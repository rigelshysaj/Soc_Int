package org.example.Entity;

import lombok.Data;
import org.example.Enum.EventCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime eventDateTime;
    private LocalDateTime eventStartDateTime;
    private String location;
    private int maxParticipants;
    private LocalDateTime eventDeadline;
    private double latitude;
    private double longitude;

    @ManyToOne
    private UserEntity host;

    @ManyToMany
    private List<UserEntity> participants;

    @OneToMany(mappedBy = "event")
    private List<FeedbackEntity> feedbacks;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @Enumerated(EnumType.STRING)
    private EventCategory category;

}
