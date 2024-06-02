package com.G23.ParkIt.controller.testController;
import com.G23.ParkIt.controller.UserController;
import com.G23.ParkIt.entity.User;
import com.G23.ParkIt.service.UserService;
import com.G23.ParkIt.util.JwtUtil;
import com.G23.ParkIt.util.PasswordServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private PasswordServiceUtil passwordServiceUtil;
    @Mock
    private JwtUtil jwtUtil;
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testShowAllUsers() {
        // Setup
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        // Execute
        List<User> users = userController.showAllUsers();

        // Assert
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    public void testGetUserByIdSuccess() {
        // Setup
        User user = new User();
        user.setUsername("user1");

        when(jwtUtil.extractUsername(anyString())).thenReturn("user1"); // Mocking JWT extraction
        when(userService.getUserId("user1")).thenReturn(user); // Mocking the retrieval of user by username
        when(userService.getUserById(user.getUserId())).thenReturn(user); // Mocking the retrieval of user by user ID

        // Execute
        ResponseEntity<User> response = userController.getUserById("Bearer sample_token");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("user1", response.getBody().getUsername());
    }
    @Test
    public void testRegisterWithDuplicateUsername() {
        // Setup
        User user = new User();
        user.setUsername("duplicateUser");
        user.setPassword("somePassword");
        when(userService.getUserByUsername("duplicateUser")).thenReturn(user);

        // Mocking password match check
        when(passwordServiceUtil.matches(anyString(), anyString())).thenReturn(true);

        // Execute
        ResponseEntity<String> response = userController.register(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Duplicate user name. ", response.getBody());
    }
}

