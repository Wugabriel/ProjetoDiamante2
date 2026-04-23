package com.diamante.user.controller;

import com.diamante.user.entity.User;
import com.diamante.user.entity.UserPreferences;
import com.diamante.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(user));
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/{id}/preferences")
    public ResponseEntity<UserPreferences> savePreferences(
            @PathVariable Long id,
            @RequestBody UserPreferences preferences) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.savePreferences(id, preferences));
    }

    @GetMapping("/{id}/preferences")
    public UserPreferences getPreferences(@PathVariable Long id) {
        return service.getPreferences(id);
    }
}
