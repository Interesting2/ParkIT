package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.User;
import com.G23.ParkIt.mapper.UserMapper;
import com.G23.ParkIt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }
    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
    // @Override
    // public void insertUserWithoutVerify(User user) {
    //     userMapper.insertUserWithoutVerify(user);
    // }
    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
    @Override
    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }
    @Override
    public User getUserId(String username) {
        return userMapper.getUserId(username);
    }
    @Override
    public User getUserByUsername(String username) {return userMapper.getUserByUsername(username);}
    @Override
    public void updateVerifyCodeForUser(String username, String verifyCode){
        userMapper.updateVerifyCodeForUser(username, verifyCode);
    }
    @Override
    public String getVerifyCodeByUsername(String username) {
        return userMapper.getVerifyCodeByUsername(username);
    }

    // public void activateUser(String username) {
    //     userMapper.activateUser(username);
    // }
    // public void deactivateUser(String username) {
    //     userMapper.deactivateUser(username);
    // }

    public void resetPassword(String username, String newPassword) {
        userMapper.resetPassword(username, newPassword);
    }
}
