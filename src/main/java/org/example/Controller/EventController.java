package org.example.Controller;

import lombok.AllArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.example.DTO.EventDTO;
import org.example.DTO.FeedbackDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.*;
import org.example.Enum.EventCategory;
import org.example.Request.ConfirmUserRequest;
import org.example.Request.LeaveFeedbackRequest;
import org.example.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@AllArgsConstructor
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @PostMapping("/confirm-user")
    public ResponseEntity<Boolean> confirmUser(@RequestBody ConfirmUserRequest request) {
        UserDTO host = request.getHost();
        UserDTO user = request.getUser();
        EventDTO event = request.getEvent();
        boolean confirmationResult = eventService.confirmUser(host, user, event);
        return ResponseEntity.ok(confirmationResult);
    }

    @GetMapping("/filter-events")
    public ResponseEntity<List<EventDTO>> filterEventsByPreferences(@RequestParam Long userId) {
        UserDTO user = userService.getUserById(userId);
        if (user != null) {
            List<EventDTO> filteredEvents = eventService.filterEventsByPreferences(user);
            return ResponseEntity.ok(filteredEvents);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @PostMapping("/create-event")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PostMapping("/join-event")
    public ResponseEntity<String> joinEvent(@RequestParam Long userId, @RequestParam Long eventId) {
        UserDTO user = userService.getUserById(userId);
        EventDTO event = eventService.getEventById(eventId);

        if (user == null || event == null) {
            return ResponseEntity.badRequest().body("User or event not found");
        }

        boolean joined = eventService.joinEvent(user, event);

        if (joined) {
            return ResponseEntity.ok("Joined the event successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to join the event");
        }
    }

    @PostMapping("/confirm-participation")
    public boolean confirmParticipation(@RequestParam Long hostId, @RequestParam Long eventId, @RequestParam Long userId) {
        UserDTO host = userService.getUserById(hostId);
        EventDTO event = eventService.getEventById(eventId);
        UserDTO user = userService.getUserById(userId);

        if (event != null && host != null && user != null) {
            if (event.getHost().equals(host) && event.isEventEnded()) {
                return eventService.confirmUser(host, user, event);
            }
        }
        return false;
    }

    @PostMapping("/end-event")
    public ResponseEntity<String> endEvent(@RequestParam Long eventId, @RequestParam Long hostId) {
        boolean ended = eventService.endEvent(eventId, hostId);

        if (ended) {
            return ResponseEntity.ok("Event has been ended.");
        } else {
            return ResponseEntity.badRequest().body("Event could not be ended.");
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId) {
        EventDTO event = eventService.getEventById(eventId);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/in-range")
    public ResponseEntity<List<EventDTO>> getEventsInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {

        List<EventDTO> events = eventService.getEventsInRange(startTime, endTime);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/in-range-and-distance")
    public ResponseEntity<List<EventDTO>> getEventsInRangeAndDistance(
            @RequestParam double userLatitude,
            @RequestParam double userLongitude,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        List<EventDTO> events = eventService.getEventsInRangeAndDistance(userLatitude, userLongitude, startTime, endTime);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<EventDTO>> getEventsByUserAndTimeRange(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {

        List<EventDTO> events = eventService.getEventsByUserAndTimeRange(userId, startTime, endTime);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/by-user-and-distance")
    public ResponseEntity<List<EventDTO>> getEventsByUserAndTimeRangeAndDistance(
            @RequestParam Long userId,
            @RequestParam double userLatitude,
            @RequestParam double userLongitude,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        List<EventDTO> events = eventService.getEventsByUserAndTimeRangeAndDistance(userId, userLatitude, userLongitude, startTime, endTime);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/category")
    public List<EventDTO> getEventsByCategoryAndTimeRange(
            @RequestParam EventCategory category,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return eventService.getEventsByCategoryAndTimeRange(category, startTime, endTime);
    }

    @GetMapping("/events/category-and-distance")
    public ResponseEntity<List<EventDTO>> getEventsByCategoryAndTimeRangeAndDistance(
            @RequestParam EventCategory category,
            @RequestParam double userLatitude,
            @RequestParam double userLongitude,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        List<EventDTO> events = eventService.getEventsByCategoryAndTimeRangeAndDistance(category, userLatitude, userLongitude, startTime, endTime);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/user-category")
    public List<EventDTO> getEventsByUserAndCategoryAndTimeRange(
            @RequestParam Long userId,
            @RequestParam EventCategory category,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return eventService.getEventsByUserAndCategoryAndTimeRange(userId, category, startTime, endTime);
    }

    @GetMapping("/events/user-category-and-distance")
    public ResponseEntity<List<EventDTO>> getEventsByUserAndCategoryAndTimeRangeAndDistance(
            @RequestParam Long userId,
            @RequestParam EventCategory category,
            @RequestParam double userLatitude,
            @RequestParam double userLongitude,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        List<EventDTO> events = eventService.getEventsByUserAndCategoryAndTimeRangeAndDistance(userId, category, userLatitude, userLongitude, startTime, endTime);
        return ResponseEntity.ok(events);
    }
}