package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest req) {
        Optional<User> exists = userRepository.findByEmail(req.getEmail());
        if (exists.isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        String pwHash = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12));
        User u = new User();
        u.setEmail(req.getEmail());
        u.setPasswordHash(pwHash);
        u.setDisplayName(req.getDisplayName());
        u.setRole(req.getRole() == null ? "candidate" : req.getRole());
        return userRepository.save(u);
    }
}
