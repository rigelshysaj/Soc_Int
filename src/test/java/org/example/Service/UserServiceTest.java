package org.example.Service;

import org.example.Enum.EventCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.example.DTO.UserDTO;


import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test") // Usa il profilo "test" definito nel tuo application.properties
@Transactional // Assicura il rollback delle transazioni dopo ogni test
public class UserServiceTest {
    @Autowired
    private UserService userService;


    @Test
    public void testGetUserById() {
        // Creare un utente fittizio da inserire nel database
        UserDTO userDTO = new UserDTO(null, "test@example.com", "Test User", Collections.singletonList(EventCategory.SPORTS));
        userDTO.setEmail("test@example.com");
        userDTO.setName("Test User");
        userDTO.setPreferences(Collections.singletonList(EventCategory.SPORTS)); // Imposta le preferenze
        UserDTO savedUser = userService.createUser(userDTO);

        // Ottenere l'utente per ID
        UserDTO retrievedUser = userService.getUserById(savedUser.getId());

        // Verifica che l'utente sia stato recuperato correttamente
        assertNotNull(retrievedUser);
        assertEquals(savedUser.getId(), retrievedUser.getId());
        assertEquals(savedUser.getEmail(), retrievedUser.getEmail());
        assertEquals(savedUser.getName(), retrievedUser.getName());
        assertEquals(savedUser.getPreferences(), retrievedUser.getPreferences()); // Verifica le preferenze
    }

}

