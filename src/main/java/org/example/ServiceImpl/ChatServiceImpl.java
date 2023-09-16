package org.example.ServiceImpl;

import lombok.AllArgsConstructor;
import org.example.DTO.ChatDTO;
import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.ChatEntity;
import org.example.Entity.EventEntity;
import org.example.Repository.ChatRepository;
import org.example.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final Map<Long, ChatDTO> eventChatMap = new HashMap<>();

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ChatDTO createChat(ChatDTO chatDTO) {
        ChatEntity chatEntity = modelMapper.map(chatDTO, ChatEntity.class);
        chatEntity.setActive(true);
        chatRepository.save(chatEntity);
        return chatDTO;
    }

    @Override
    public void closeChatForEvent(Long eventId) {
        ChatEntity chatEntity = chatRepository.findById(eventId).orElse(null);
        if (chatEntity != null) {
            chatEntity.setActive(false); // Imposta la chat come chiusa
            chatRepository.save(chatEntity);
        }
    }

    @Override
    public void createChatForEvent(EventDTO event) {
        ChatDTO chat = new ChatDTO(event.getId());
        eventChatMap.put(event.getId(), chat); // Memorizza la chat nella mappa
    }

    @Override
    public ChatDTO getChatForEvent(Long eventId) {
        ChatEntity chatEntity = chatRepository.findById(eventId).orElse(null);
        if (chatEntity != null && chatEntity.isActive()) { // Verifica se la chat è attiva
            return modelMapper.map(chatEntity, ChatDTO.class);
        }
        return null;
    }

    @Override
    public void sendMessage(UserDTO user, EventDTO event, String messageText) {
        ChatDTO chat = eventChatMap.get(event.getId()); // Ottieni la chat direttamente dalla mappa
        if (chat != null) {
            if (event.getParticipationConfirmation().get(user.getId())) {
                chat.addMessage(user.getName() + ": " + messageText);
            } else {
                System.out.println("User is not confirmed for the event.");
            }
        } else {
            System.out.println("Chat for the event not found.");
        }
    }
}