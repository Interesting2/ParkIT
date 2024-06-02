package com.G23.ParkIt.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String password;
    private String username;
    private Integer contactPhoneNumber;
    private String contactEmail;
    private String verifyCode;
    private Integer verified;
}

