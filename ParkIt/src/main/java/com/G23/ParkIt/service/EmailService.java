package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.EmailDetails;


public interface EmailService {
    boolean sendMail(EmailDetails details); 
}
