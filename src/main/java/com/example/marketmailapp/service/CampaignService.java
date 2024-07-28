package com.example.marketmailapp.service;

import com.example.marketmailapp.dto.CampaignDTO;

import java.util.List;

public interface CampaignService {

     CampaignDTO createCampaign(CampaignDTO campaignDTO);

     List<CampaignDTO> getCampaignsByClientId(Long clientId);

     void sendCampaignMail(Long campaignId);
}
