package com.omni.code.service;

import com.omni.code.entity.User;
import com.omni.code.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    public void createUser(User user) {
        userMapper.createUser(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");  // Set a default role
        userMapper.registerUser(user);
    }

    public User loginUser(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            // Log and return null for invalid inputs
            System.out.println("Invalid login attempt: email or password is null/empty.");
            return null;
        }
        User user = userMapper.findUserByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean checkIfUserExistsByEmail(String email) {
        // Logic to check if a user with this email exists in the database.
        return userMapper.existsByEmail(email); // Assuming you have such a method.
    }

    public void updatePassword(String email, String newPassword) {
        User user = userMapper.findByEmail(email);
        if (user != null) {
            // Encrypt the password before saving
            user.setPassword(passwordEncoder.encode(newPassword));
            userMapper.updatePassword(user.getEmail(),user.getPassword());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public boolean isUserAdmin(String userId) {
        return userMapper.isUserAdmin(userId);
    }
}
