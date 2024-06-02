package com.G23.ParkIt.mapper;
import com.G23.ParkIt.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {
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
