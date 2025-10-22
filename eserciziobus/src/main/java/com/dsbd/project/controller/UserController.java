package com.dsbd.project.controller;

import com.dsbd.project.entity.User;
import com.dsbd.project.security.AuthResponse;
import com.dsbd.project.security.BasicAuthRequest;
import com.dsbd.project.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProjectUserService userService;

    // ------------------- REGISTRAZIONE -------------------
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singletonList("USER"));
        }
        if (user.getCredit() == null) {
            user.setCredit(BigDecimal.ZERO);
        }
        User newUser = userService.registerUser(user);
        return ResponseEntity.status(201).body(newUser);
    }

    // ------------------- LOGIN -------------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody BasicAuthRequest request) {
        String token = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token, null));
    }

    // ------------------- AUTENTICATO -------------------
    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // ------------------- CREDITO -------------------
    @PatchMapping("/me/credit/toup")
    public ResponseEntity<User> topUp(@RequestParam BigDecimal amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User updatedUser = userService.topUp(email, amount);
        return ResponseEntity.ok(updatedUser);
    }
}
