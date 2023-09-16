package org.example.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChatEntityMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    private String message;
}

