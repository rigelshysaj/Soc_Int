package org.example.Service;

import org.example.DTO.ChatDTO;
import org.example.DTO.EventDTO;
import org.example.DTO.UserDTO;
import org.example.Enum.EventCategory;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDTO createEvent(EventDTO event);
    boolean confirmUser(UserDTO host, UserDTO user, EventDTO event);
    boolean endEvent(Long eventId, Long hostId);
    boolean joinEvent(UserDTO user, EventDTO event);
    List<EventDTO> filterEventsByPreferences(UserDTO user);

    ChatDTO getChatForEvent(Long eventId);

    EventDTO getEventById(Long eventId);

    boolean isParticipationConfirmed(UserDTO host, UserDTO user, EventDTO event);

    List<EventDTO> filterEventsByPreferences(UserDTO user, double userLatitude, double userLongitude);

    List<EventDTO> getEventsInRange(LocalDateTime startTime, LocalDateTime endTime);
    List<EventDTO> getEventsInRangeAndDistance(double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime);

    List<EventDTO> getEventsByUserAndTimeRange(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    List<EventDTO> getEventsByUserAndTimeRangeAndDistance(Long userId, double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime);
    List<EventDTO> getEventsByCategoryAndTimeRange(EventCategory category, LocalDateTime startTime, LocalDateTime endTime);

    List<EventDTO> getEventsByCategoryAndTimeRangeAndDistance(EventCategory category, double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime);
    List<EventDTO> getEventsByUserAndCategoryAndTimeRange(
            Long userId, EventCategory category, LocalDateTime startTime, LocalDateTime endTime);
    List<EventDTO> getEventsByUserAndCategoryAndTimeRangeAndDistance(Long userId, EventCategory category, double userLatitude, double userLongitude, LocalDateTime startTime, LocalDateTime endTime);
}
