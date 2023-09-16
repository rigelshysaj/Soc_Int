package org.example.ServiceImpl;

import lombok.AllArgsConstructor;
import org.example.DTO.ChatDTO;
import org.example.DTO.EventDTO;
import org.example.DTO.FeedbackDTO;
import org.example.DTO.UserDTO;
import org.example.Entity.ChatEntity;
import org.example.Entity.FeedbackEntity;
import org.example.Repository.FeedbackRepository;
import org.example.Service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private final FeedbackRepository feedbackRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO feedbackDTO) {
        FeedbackEntity feedbackEntity = modelMapper.map(feedbackDTO, FeedbackEntity.class);
        feedbackRepository.save(feedbackEntity);
        return feedbackDTO;
    }

    public FeedbackDTO leaveFeedback(UserDTO user, EventDTO event, String comment, int rating) {
        FeedbackDTO feedback = null;
        if(event.isUserParticipated(user)){
            feedback = new FeedbackDTO(null, comment, rating, event);
        }
        return feedback;
    }
}
