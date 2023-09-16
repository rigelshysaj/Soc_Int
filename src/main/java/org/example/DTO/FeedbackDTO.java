package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedbackDTO {
    private Long id;
    private String comment;
    private int rating;
    //private boolean confirmedByHost;
    private EventDTO event;
}

