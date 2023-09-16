package org.example.ServiceImpl;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.example.DTO.ChatDTO;
import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.ChatEntity;
import org.example.Entity.EventEntity;
import org.example.Entity.UserEntity;
import org.example.Enum.EventCategory;
import org.example.Repository.EventRepository;
import org.example.Repository.UserRepository;
import org.example.Service.ChatService;
import org.example.Service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private static final double MAX_DISTANCE_KM = 5.0;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        ChatDTO chatDTO = chatService.createChat(new ChatDTO(eventDTO.getId()));
        eventDTO.setChatDTO(chatDTO);

        EventEntity eventEntity = modelMapper.map(eventDTO, EventEntity.class);

        // Aggiunta del creatore all'evento
        eventDTO.getParticipants().add(eventDTO.getHost());
        eventDTO.getParticipationConfirmation().put(eventDTO.getHost().getId(), true);

        eventEntity.setChat(modelMapper.map(chatDTO, ChatEntity.class));  // Mapping della chat e impostazione nell'entità

        eventRepository.save(eventEntity);
        return eventDTO;
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        return modelMapper.map(eventEntity, EventDTO.class);
    }
    @Override
    public boolean confirmUser(UserDTO host, UserDTO user, EventDTO event) {
        if (!event.getHost().equals(host) || !event.getParticipants().contains(user) || !event.isEventEnded()) {
            return false;
        }

        event.getParticipationConfirmation().put(user.getId(), true);
        return true;
    }

    @Override
    public boolean endEvent(Long eventId, Long hostId) {
        EventEntity eventEntity = eventRepository.findById(eventId).orElse(null);
        UserEntity hostEntity = userRepository.findById(hostId).orElse(null);

        if (eventEntity != null && eventEntity.getHost().equals(hostEntity)) {
            chatService.closeChatForEvent(eventId);
            eventEntity.setEventDeadline(LocalDateTime.now());
            eventRepository.save(eventEntity); // Salvataggio dell'entità modificata
            return true;
        }

        return false;
    }

    @Override
    public boolean joinEvent(UserDTO user, EventDTO event) {
        if (event.getParticipants().contains(user) || event.getParticipants().size() >= event.getMaxParticipants() || event.isEventEnded()) {
            return false;
        }

        event.getParticipants().add(user);
        event.getParticipationConfirmation().put(user.getId(), false);
        ChatDTO eventChat = getChatForEvent(event.getId());
        eventChat.addMessage(user.getName() + ": Hi everyone, looking forward to the event!");
        return true;
    }

    @Override
    public ChatDTO getChatForEvent(Long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId).orElse(null);
        if (eventEntity != null) {
            ChatEntity chatEntity = eventEntity.getChat();
            return modelMapper.map(chatEntity, ChatDTO.class);
        }
        return null;
    }

    @Override
    public List<EventDTO> filterEventsByPreferences(UserDTO user) {
        List<EventEntity> events = eventRepository.findAll();
        List<EventDTO> filteredEvents = new ArrayList<>();
        for (EventEntity event : events) {
            if (user.getPreferences().contains(event.getCategory())) {
                filteredEvents.add(modelMapper.map(event, EventDTO.class));
            }
        }
        return filteredEvents;
    }

    public boolean isParticipationConfirmed(UserDTO host, UserDTO user, EventDTO event) {
        if (!event.getHost().equals(host) || !event.getParticipants().contains(user) || !event.isEventEnded()) {
            return false;
        }

        return event.getParticipationConfirmation().get(user.getId());
    }

    @Override
    public List<EventDTO> filterEventsByPreferences(UserDTO user, double userLatitude, double userLongitude) {
        List<EventEntity> events = eventRepository.findAll();
        List<EventDTO> filteredEvents = new ArrayList<>();

        for (EventEntity event : events) {
            if (user.getPreferences().contains(event.getCategory())) {
                double eventLatitude = event.getLatitude();
                double eventLongitude = event.getLongitude();

                double distance = calculateDistance(userLatitude, userLongitude, eventLatitude, eventLongitude);

                EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
                if (distance <= MAX_DISTANCE_KM && !eventDTO.isEventEnded()) {
                    filteredEvents.add(eventDTO);
                }
            }
        }

        return filteredEvents;
    }

    public List<EventDTO> getEventsInRange(LocalDateTime startTime, LocalDateTime endTime) {
        List<EventEntity> events = eventRepository.findByStartTimeBetween(startTime, endTime);

        return fromEntityToDto(events);
    }

    public List<EventDTO> getEventsInRangeAndDistance(double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime) {
        List<EventDTO> events = getEventsInRange(startTime, endTime);
        return filterEventsByDistanceAndMap(events, userLatitude, userLongitude);
    }

    public List<EventDTO> getEventsByUserAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<EventEntity> events = eventRepository.findByUserAndStartTimeBetween(user, startTime, endTime);

        return fromEntityToDto(events);
    }

    public List<EventDTO> getEventsByUserAndTimeRangeAndDistance(Long userId, double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime) {
        List<EventDTO> events = getEventsByUserAndTimeRange(userId, startTime, endTime);
        return filterEventsByDistanceAndMap(events, userLatitude, userLongitude);
    }

    public List<EventDTO> getEventsByCategoryAndTimeRange(EventCategory category, LocalDateTime startTime, LocalDateTime endTime) {
        List<EventEntity> events = eventRepository.findByCategoryAndStartTimeBetween(category, startTime, endTime);

        return fromEntityToDto(events);
    }

    public List<EventDTO> getEventsByCategoryAndTimeRangeAndDistance(EventCategory category, double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime) {
        List<EventDTO> events = getEventsByCategoryAndTimeRange(category, startTime, endTime);
        return filterEventsByDistanceAndMap(events, userLatitude, userLongitude);
    }

    public List<EventDTO> getEventsByUserAndCategoryAndTimeRange(
            Long userId, EventCategory category, LocalDateTime startTime, LocalDateTime endTime) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<EventEntity> events = eventRepository.findByUserAndCategoryAndStartTimeBetween(user, category, startTime, endTime);

        return fromEntityToDto(events);
    }

    public List<EventDTO> getEventsByUserAndCategoryAndTimeRangeAndDistance(Long userId, EventCategory category, double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime) {
        List<EventDTO> events = getEventsByUserAndCategoryAndTimeRange(userId, category, startTime, endTime);
        return filterEventsByDistanceAndMap(events, userLatitude, userLongitude);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raggio medio della Terra in chilometri

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private List<EventDTO> filterEventsByDistanceAndMap(List<EventDTO> events, double userLatitude, double userLongitude) {
        return events.stream()
                .filter(event -> calculateDistance(userLatitude, userLongitude, event.getLatitude(), event.getLongitude()) <= MAX_DISTANCE_KM)
                .collect(Collectors.toList());
    }

    public List<EventDTO> fromEntityToDto(List<EventEntity> events){
        List<EventDTO> eventDTOList = new ArrayList<>();

        for(EventEntity eventEntity : events){
            eventDTOList.add(modelMapper.map(eventEntity, EventDTO.class));
        }

        return eventDTOList;
    }

}
