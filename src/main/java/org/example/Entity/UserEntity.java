package org.example.Entity;

import lombok.Data;
import org.example.Enum.EventCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<EventCategory> preferences = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    private List<EventEntity> events = new ArrayList<>();
}