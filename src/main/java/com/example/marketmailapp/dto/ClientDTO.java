package com.example.marketmailapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    List<CampaignDTO> campaigns;
}
