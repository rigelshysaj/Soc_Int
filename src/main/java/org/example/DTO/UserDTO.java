package org.example.DTO;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.Enum.EventCategory;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private List<EventCategory> preferences;

    public void addPreference(EventCategory category) {
        preferences.add(category);
    }
}

