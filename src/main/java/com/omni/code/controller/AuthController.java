package com.omni.code.controller;

import com.omni.code.entity.User;
import com.omni.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://209.126.0.170" })
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            boolean userExists = userService.checkIfUserExistsByEmail(user.getEmail());
            if(userExists){
                return ResponseEntity.ok("User has already been registered");
            }
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }


    // Reset password -  Update password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        // Check if email and password are not null or empty
        if (email == null || newPassword == null || email.isEmpty() || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Email or password cannot be empty.");
        }
        boolean userExists = userService.checkIfUserExistsByEmail(email);
        if (userExists) {
            try {
                userService.updatePassword(email, newPassword);
                return ResponseEntity.ok("Password has been reset successfully..");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Password reset failed: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(400).body("Email does not exist.");
        }

    }

}
