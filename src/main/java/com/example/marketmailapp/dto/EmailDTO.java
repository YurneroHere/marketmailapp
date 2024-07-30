package com.example.marketmailapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class EmailDTO {
    private String from;
    private List<String> to;
    private String subject;
    private String body;
    public EmailDTO(String from, List<String> to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
