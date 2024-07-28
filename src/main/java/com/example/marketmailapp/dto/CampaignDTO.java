package com.example.marketmailapp.dto;

import com.example.marketmailapp.enums.MailStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CampaignDTO {
    private Long id;
    private String name;
    private String subject;
    private String emailBody;
    private MailStatus status;
    private List<String> subscribers;
    private String client_eMail;
    private Long client_id;

}
