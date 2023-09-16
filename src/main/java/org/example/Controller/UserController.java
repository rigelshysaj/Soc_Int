package org.example.Controller;

import lombok.AllArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.example.DTO.UserDTO;
import org.example.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController{
    @Autowired
    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

}