package com.example.marketmailapp.utils;

import com.example.marketmailapp.dto.EmailDTO;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public String sendEmail(EmailDTO emailDTO) {
        if(!validateEmailsIds(emailDTO.getTo(), emailDTO.getFrom())) {
            return "Invalid email address";
        }
        System.out.println("Sending email to " + emailDTO.getTo() + " with subject " + emailDTO.getSubject());
        return "SENT";
    }
    @Override
    public Boolean validateEmailsIds(List<String> to, String from){
        //implement validation
        return isValidEmail(from) && to.stream().allMatch(EmailServiceImpl::isValidEmail);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return true;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
