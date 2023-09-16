package org.example.ServiceImpl;

import lombok.AllArgsConstructor;
import org.example.DTO.EntityDTOConverter;
import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.UserEntity;
import org.example.Repository.UserRepository;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityDTOConverter entityDTOConverter; // Il tuo componente per le conversioni

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = EntityDTOConverter.convertToEntity(userDTO);
        userRepository.save(userEntity);
        return userDTO;
    }
    @Override
    public UserDTO getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        return EntityDTOConverter.convertToDTO(userEntity);
    }

    public void confirmUser(UserDTO host, UserDTO user, EventDTO event) {
        if (!event.getHost().equals(host)) {
            return;
        }

        if (!event.getParticipants().contains(user)) {
            return;
        }

        if (!event.isEventEnded()) {
            return;
        }

        event.confirmParticipation(user);
    }


    public boolean isUserParticipated(UserDTO user, EventDTO event) {
        return event.getParticipationConfirmation().get(user.getId());
    }


    public boolean endEvent(UserDTO host, EventDTO event) {
        if (!event.getHost().equals(host)) {
            return false;
        }

        event.setEventDeadline(LocalDateTime.now());
        return true;
    }

    public boolean joinEvent(UserDTO user, EventDTO event) {
        if (event.getParticipants().contains(user)) {
            return false;
        }

        if (event.getParticipants().size() >= event.getMaxParticipants()) {
            return false;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        if (currentDateTime.isAfter(event.getEventDeadline())) {
            return false;
        }

        event.getParticipants().add(user);
        event.getParticipationConfirmation().put(user.getId(), false);

        return true;
    }
}