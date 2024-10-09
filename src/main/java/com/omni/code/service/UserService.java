package com.omni.code.service;

import com.omni.code.entity.User;
import com.omni.code.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}
