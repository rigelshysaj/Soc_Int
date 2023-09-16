package org.example.Service;

import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.UserEntity;

public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO getUserById(Long userId);
    void confirmUser(UserDTO host, UserDTO user, EventDTO event);

    //boolean isEventEnded(EventDTO event);
    boolean isUserParticipated(UserDTO user, EventDTO event);
    boolean endEvent(UserDTO host, EventDTO event);

    boolean joinEvent(UserDTO user, EventDTO event);


}