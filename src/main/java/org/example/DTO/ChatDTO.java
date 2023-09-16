package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChatDTO {

    private Long id;
    private Long eventId;

    private boolean isActive;

    @ElementCollection
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public ChatDTO(Long eventId) {
        this.eventId = eventId;
    }
}

