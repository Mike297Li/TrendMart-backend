package com.omni.code.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data                   // Lombok will generate getter and setter methods automatically
@NoArgsConstructor       // Generates a no-args constructor
@AllArgsConstructor      // Generates an all-args constructor
public class User {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String address;
    private String role;
    private LocalDateTime createdAt;

}
