package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.EmailDetails;
import com.G23.ParkIt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender javaMailSender;
    public boolean sendMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage(); 

            System.out.println("sender: " + sender); 

            mailMessage.setFrom(sender); 
            mailMessage.setTo(details.getRecipient()); 
            mailMessage.setText(details.getMsgBody()); 
            mailMessage.setSubject(details.getSubject()); 

            javaMailSender.send(mailMessage);

            return true; 
        }
        catch (Exception e) {
            // e.printStackTrace();
            return false; 
        }
    }
}
