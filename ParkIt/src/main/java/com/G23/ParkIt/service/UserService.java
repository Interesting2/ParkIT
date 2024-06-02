package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.User;
import java.util.List;

public interface UserService {
    User getUserById(Integer userId);
    List<User> getAllUsers();
    // void insertUserWithoutVerify(User user);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(Integer userId);
    User getUserId(String username);
    User getUserByUsername(String username);
    void updateVerifyCodeForUser(String username, String verifyCode);
    String getVerifyCodeByUsername(String username);
    // void activateUser(String username);
    // void deactivateUser(String username);
    void resetPassword(String username, String newPassword);
}
