package com.ShelfTracker.api.controller;

import com.ShelfTracker.api.model.User;
import com.ShelfTracker.api.repository.DataStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // Hanapin kung umiiral ang user credentials sa ating DataStore
        User matchedUser = DataStore.USERS.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (matchedUser == null) {
            // Kapag mali ang detalye, mag-return ng 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Maling Username o Password. Subukan muli."));
        }

        // Kapag matagumpay ang login, ibalik ang username at ang kanyang role
        return ResponseEntity.ok(Map.of(
            "message", "Login successful!",
            "username", matchedUser.getUsername(),
            "role", matchedUser.getRole()
        ));
    }
}