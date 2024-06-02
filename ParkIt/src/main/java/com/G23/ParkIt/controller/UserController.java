package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.EmailDetails;
import com.G23.ParkIt.entity.User;
import com.G23.ParkIt.service.EmailService;
import com.G23.ParkIt.service.UserService;
import com.G23.ParkIt.util.JwtUtil;
import com.G23.ParkIt.util.PasswordServiceUtil;
import com.G23.ParkIt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.G23.ParkIt.entity.VerificationDTO; 
import com.G23.ParkIt.entity.ResetPasswordDTO; 

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordServiceUtil passwordServiceUtil;
    @Autowired
    EmailService emailService;
    @GetMapping("/getAllUser")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    @GetMapping("/getUserById")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract the token from "Bearer [token]".
            String username = jwtUtil.extractUsername(token);

            Integer userId = userService.getUserId(username).getUserId();
            try {
                User user = userService.getUserById(userId);
                if(user != null) {
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            } catch(Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String username = user.getUsername();
        String rawPassword = user.getPassword(); // origin password
        String rec = user.getContactEmail();

        // use passwordServiceUtil to encrypt password
        String encryptedPassword = passwordServiceUtil.encodePassword(rawPassword);
        user.setPassword(encryptedPassword);
        user.setVerified(0);

        User gottenUser = userService.getUserByUsername(username);
        if(gottenUser != null) {
            return new ResponseEntity<>("Duplicate user name. ", HttpStatus.OK);
        }

        userService.insertUser(user);

        String veriCode = emailVeriCode(rec);

        if(veriCode == null)
            return new ResponseEntity<>("Send verification code failed. ", HttpStatus.INTERNAL_SERVER_ERROR);

        //putCodeToDatabase(username, veriCode);
        userService.updateVerifyCodeForUser(username, veriCode);

        return new ResponseEntity<>("Send verification code successfully! " + veriCode, HttpStatus.OK);
    }


    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    @PostMapping("/registerVerify")
    public ResponseEntity<String> registerVerify(@RequestBody VerificationDTO verificationRequest) {
        String username = verificationRequest.getUserName();
        String code = verificationRequest.getCode();
        System.out.printf("username: %s, code: %s", username, code); 

        Integer userId = userService.getUserByUsername(username).getUserId(); 
        User savedUser = userService.getUserById(userId); 

        if(userService.getVerifyCodeByUsername(username).equals(code)) {
            // activateUser(username);
            savedUser.setVerified(1);
            userService.updateUser(savedUser);
            return new ResponseEntity<>("Registration completed! ", HttpStatus.OK);
        }
        //deactivateUser(username);
        // userService.deactivateUser(username);
        savedUser.setVerified(0);
        userService.updateUser(savedUser);
        return new ResponseEntity<>("Verification code is wrong! ", HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User savedUser = userService.getUserByUsername(user.getUsername());

        if(savedUser.getVerified() == 0) {
            // System.out.println("not verified"); 
            return new ResponseEntity<>(Collections.singletonMap("message", "Login failed, contact_email is not verified. "), HttpStatus.UNAUTHORIZED);
        }
        // use passwordServiceUtil.matches to verify
        String rawPassword = user.getPassword();
        if (savedUser != null && passwordServiceUtil.matches(rawPassword, savedUser.getPassword())) {
            String token = jwtUtil.generateToken(savedUser);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("token", token);
            responseBody.put("message", "Login successful");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.singletonMap("message", "Login failed"), HttpStatus.UNAUTHORIZED);
    }
    
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    @PostMapping("/forgotPassword")
    // Reset password with sending email. 
    public ResponseEntity<String> resetPassword(@RequestBody User username) {
        try {
            User user = userService.getUserByUsername(username.getUsername());
            String email = user.getContactEmail(); 

            String veriCode = emailVeriCode(email);
            if(veriCode == null)
                return new ResponseEntity<>("Send verification code failed. ", HttpStatus.INTERNAL_SERVER_ERROR); 
                
            //putCodeToDatabase(username.getUsername(), veriCode);
            userService.updateVerifyCodeForUser(username.getUsername(), veriCode);
            return new ResponseEntity<>("Send verification code successfully! ", HttpStatus.OK); 
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK); 
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    // Forget password. Firstly, request without verificatio code. Secondly, with code. 
    @PostMapping("/resetPassword")
    public ResponseEntity<String> forgetVerify(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        String username = resetPasswordDTO.getUserName(); 
        String newPass = resetPasswordDTO.getNewPass(); 
        newPass = passwordServiceUtil.encodePassword(newPass);
        String code = resetPasswordDTO.getCode(); 

        if(code != null) {
            String savedCode = userService.getVerifyCodeByUsername(username);
            if(code.equals(savedCode)) {
                //resetPasswordToDatabase(username, newPass);
                userService.resetPassword(username, newPass);
                return new ResponseEntity<>("Password reset successfully! ", HttpStatus.OK); 
            }
            else
                return new ResponseEntity<>("Verification code is wrong! ", HttpStatus.OK); 
        }
        else {
            return new ResponseEntity<>("Please enter the verification code. ", HttpStatus.BAD_REQUEST); 
        }
    }

    private String emailVeriCode(String rec) {
        int digit = (int)(Math.random()*1000000);

        System.out.printf("Verify code: %d\n", digit);
        if(digit < 100000)
            digit += 100000;

        EmailDetails details = new EmailDetails();
        details.setRecipient(rec);
        details.setSubject("ParkIt Email Verification");
        details.setMsgBody(String.format("Your verification digit is %d", digit));

        if(!emailService.sendMail(details))
            return null;

        return String.valueOf(digit);
    }

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private void activateUser(String username) {
//        String query = String.format("UPDATE users SET balance=0 WHERE username=\'%s\'", username);
//        jdbcTemplate.execute(query);
//    }
//
//    private void deactivateUser(String username) {
//        String query = String.format("UPDATE users SET balance=-1 WHERE username=\'%s\'", username);
//        jdbcTemplate.execute(query);
//    }
//
//    private void resetPasswordToDatabase(String username, String newPassword) {
//        String query = String.format("UPDATE users SET password=\'%s\' WHERE username=\'%s\'", newPassword, username);
//        jdbcTemplate.execute(query);
//    }



}
