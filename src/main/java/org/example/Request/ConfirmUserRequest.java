package org.example.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.EventEntity;
import org.example.Entity.UserEntity;

@AllArgsConstructor
@Data
public class ConfirmUserRequest {
    private UserDTO host;
    private UserDTO user;
    private EventDTO event;
}