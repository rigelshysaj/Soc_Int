package org.example.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatEntityMessage> messages;

    private boolean isActive;
}