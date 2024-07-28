package com.example.marketmailapp.utils;

import com.example.marketmailapp.dto.EmailDTO;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    public void sendEmail(EmailDTO emailDTO) {
        // Mock email sending logic
        System.out.println("Sending email to " + emailDTO.getTo() + " with subject " + emailDTO.getSubject());
    }
}
