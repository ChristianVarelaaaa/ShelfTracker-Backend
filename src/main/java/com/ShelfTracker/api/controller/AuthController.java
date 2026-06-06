package com.ShelfTracker.api.controller;

import com.ShelfTracker.api.model.User;
import com.ShelfTracker.api.repository.DataStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://app.netlify.com/projects/ephemeral-strudel-07d7dc")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User matchedUser = DataStore.USERS.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (matchedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Maling Username o Password. Subukan muli."));
        }

        return ResponseEntity.ok(Map.of(
            "message", "Login successful!",
            "username", matchedUser.getUsername(),
            "role", matchedUser.getRole()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {

        String newUsername = request.get("username");
        String newPassword = request.get("password");

        if (newUsername == null || newUsername.isBlank() ||
            newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Username and password are required."));
        }

        boolean exists = DataStore.USERS.stream()
                .anyMatch(u -> u.getUsername().equals(newUsername));
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Username already exists."));
        }

        DataStore.USERS.add(new User(newUsername, newPassword, "ADMIN"));
        return ResponseEntity.ok(Map.of("message", "Account registered successfully!"));
    }
}