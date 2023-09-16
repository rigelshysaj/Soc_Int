package org.example.Service;

import org.example.DTO.EventDTO;
import org.example.DTO.FeedbackDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.FeedbackEntity;

public interface FeedbackService {
    FeedbackDTO createFeedback(FeedbackDTO feedback);

    FeedbackDTO leaveFeedback(UserDTO user, EventDTO event, String comment, int rating);
}