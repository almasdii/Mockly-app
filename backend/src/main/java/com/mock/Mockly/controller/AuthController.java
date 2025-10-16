package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            User saved = userService.register(req);
            // возвращаем минимально — id и email
            return ResponseEntity.ok().body(
                    new java.util.HashMap<String, Object>() {{
                        put("id", saved.getId());
                        put("email", saved.getEmail());
                        put("displayName", saved.getDisplayName());
                        put("role", saved.getRole());
                    }}
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
        }
    }
}
