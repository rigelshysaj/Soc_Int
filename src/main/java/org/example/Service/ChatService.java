package org.example.Service;

import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.ChatEntity;
import org.example.DTO.ChatDTO;

public interface ChatService {
    ChatDTO createChat(ChatDTO chat);
    void createChatForEvent(EventDTO event);
    ChatDTO getChatForEvent(Long eventId);
    void sendMessage(UserDTO user, EventDTO event, String messageText);
    void closeChatForEvent(Long eventId);
}
