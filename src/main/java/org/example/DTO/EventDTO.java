package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.Entity.ChatEntity;
import org.example.Entity.FeedbackEntity;
import org.example.Entity.UserEntity;
import org.example.Enum.EventCategory;

import javax.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private int maxParticipants;
    private LocalDateTime eventDateTime;
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventDeadline;

    private UserDTO host;
    private List<UserDTO> participants;
    private EventCategory category;
    private List<FeedbackDTO> feedbacks;

    private ChatDTO chatDTO;

    private double latitude;
    private double longitude;

    @ElementCollection
    private Map<Long, Boolean> participationConfirmation;
    public boolean isEventEnded() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.isAfter(eventDeadline);
    }

    public boolean isUserParticipated(UserDTO user) {
        return participationConfirmation.getOrDefault(user.getId(), false);
    }
    public void confirmParticipation(UserDTO participant) {
        if (participationConfirmation.containsKey(participant.getId())) {
            participationConfirmation.put(participant.getId(), true);
        }
    }
}

