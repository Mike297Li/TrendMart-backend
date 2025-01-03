package com.omni.code.test;

import com.omni.code.entity.User;
import com.omni.code.mapper.UserMapper;
import com.omni.code.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_SuccessfulLogin() {
        // Arrange
        String email = "testuser@example.com";
        String password = "password123";
        String hashedPassword = "$2a$10$wRz6lfiX7uwzkeq5H1gL9uOmg5eGVQvhbAqIHvTlrqQXE8KoiM9tC";

        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setEmail(email);
        mockUser.setPassword(hashedPassword);

        when(userMapper.findUserByEmail(email)).thenReturn(mockUser);
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

        // Act
        User loggedInUser = userService.loginUser(email, password);

        // Assert
        assertNotNull(loggedInUser, "User should not be null on successful login.");
        assertEquals(email, loggedInUser.getEmail(), "Email should match.");
        assertEquals(1, loggedInUser.getUserId(), "User ID should match.");

        // Logging for additional visibility (optional)
        System.out.println("Login successful for user: " + loggedInUser.getEmail());
    }



    @Test
    void testLoginUser_InvalidPassword() {
        // Arrange
        String email = "testuser@example.com";
        String password = "wrongpassword";
        String hashedPassword = "$2a$10$wRz6lfiX7uwzkeq5H1gL9uOmg5eGVQvhbAqIHvTlrqQXE8KoiM9tC"; // Mock hashed password

        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setEmail(email);
        mockUser.setPassword(hashedPassword);

        when(userMapper.findUserByEmail(email)).thenReturn(mockUser);
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

        // Act
        User loggedInUser = userService.loginUser(email, password);

        // Assert
        assertNull(loggedInUser);

        verify(userMapper, times(1)).findUserByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, hashedPassword);
    }

    @Test
    void testLoginUser_UserNotFound() {
        // Arrange
        String email = "nonexistentuser@example.com";
        String password = "password123";

        when(userMapper.findUserByEmail(email)).thenReturn(null);

        // Act
        User loggedInUser = userService.loginUser(email, password);

        // Assert
        assertNull(loggedInUser);

        verify(userMapper, times(1)).findUserByEmail(email);
        verify(passwordEncoder, times(0)).matches(anyString(), anyString());
    }

    @Test
    void testLoginUser_NullEmail() {
        // Arrange
        String email = null; // Invalid email
        String password = "password123";

        // Mock behavior: userMapper.findUserByEmail should not be called
        when(userMapper.findUserByEmail(email)).thenReturn(null);

        // Act
        User loggedInUser = userService.loginUser(email, password);

        // Assert
        assertNull(loggedInUser, "Login should fail when email is null.");
        verify(userMapper, never()).findUserByEmail(anyString()); // Ensure mapper is not called

        StringBuffer stringBuffer=new StringBuffer();
        String reverse = stringBuffer.reverse().toString();
        reverse.replace(" ","");

    }

    public static boolean isPalindrome(String s) {
        String original=s.replaceAll("[^a-zA-Z0-9]", "");
        for(int i=0;i<original.length();i++){
            if(Character.toLowerCase(original.charAt(i)) - Character.toLowerCase(original.charAt(original.length()-1-i))!=0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
         String s="Was it a car or a cat I saw?";

        System.out.println(isPalindrome(s));


    }

}
