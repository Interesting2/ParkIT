package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.EmailDetails;
import com.G23.ParkIt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class EmailController {
    @Autowired
    private EmailService emailService; 

    @GetMapping("/testEmail")
    public ResponseEntity<String> testEmail(@RequestParam(value = "rec", defaultValue = "arlengood2000@gmail.com") String rec, 
                                            @RequestParam(value = "text", defaultValue = "This is a test for Spring Boot email function. ") String text) {
        EmailDetails details = new EmailDetails(); 
        
        details.setRecipient(rec); 
        details.setSubject("Spring Boot email test"); 
        details.setMsgBody(text);

        if(emailService.sendMail(details))
            return new ResponseEntity<String>("Email sent succeccfully. ", null, HttpStatus.OK); 
        return new ResponseEntity<String>("Email sent failed. ", null, HttpStatus.OK); 
    }
}
