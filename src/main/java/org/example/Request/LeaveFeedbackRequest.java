package org.example.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.EventEntity;
import org.example.Entity.UserEntity;

@AllArgsConstructor
@Data
public
class LeaveFeedbackRequest {
    private UserDTO user;
    private EventDTO event;
    private String comment;
    private int rating;
}
