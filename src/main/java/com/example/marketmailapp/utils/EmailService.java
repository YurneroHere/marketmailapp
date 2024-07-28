package com.example.marketmailapp.utils;

import com.example.marketmailapp.dto.EmailDTO;

import java.util.List;

public interface EmailService {
     Boolean validateEmailsIds(List<String> to, String from);
     String sendEmail(EmailDTO emailDTO);
}
