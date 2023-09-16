package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTO.EventDTO;
import org.example.DTO.FeedbackDTO;
import org.example.DTO.UserDTO;
import org.example.Request.LeaveFeedbackRequest;
import org.example.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@AllArgsConstructor
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private final EventService eventService;

    @PostMapping("/give-feedback")
    public ResponseEntity<FeedbackDTO> giveFeedback(@RequestBody LeaveFeedbackRequest request) {
        UserDTO user = request.getUser();
        EventDTO event = request.getEvent();
        String comment = request.getComment();
        int rating = request.getRating();

        if (user != null && event != null && !event.getHost().equals(user)) {
            // Controlla se l'ospite ha confermato la partecipazione
            if (eventService.isParticipationConfirmed(event.getHost(), user, event)) {
                FeedbackDTO feedback = feedbackService.leaveFeedback(user, event, comment, rating);
                if (feedback != null) {
                    return ResponseEntity.ok(feedback);
                } else {
                    return ResponseEntity.badRequest().build(); // Restituisci una risposta con stato di errore
                }
            } else {
                return ResponseEntity.badRequest().build(); // Messaggio di errore personalizzato
            }
        }

        return ResponseEntity.badRequest().build(); // Restituisci una risposta con stato di errore
    }

}
