package com.mangalist.manga.controller;

import com.mangalist.manga.model.User;
import com.mangalist.manga.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userRepository.save(user); // You can add password hashing later
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
