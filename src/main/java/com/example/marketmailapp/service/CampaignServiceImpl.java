package com.example.marketmailapp.service;

import com.example.marketmailapp.dto.CampaignDTO;
import com.example.marketmailapp.dto.EmailDTO;
import com.example.marketmailapp.enums.MailStatus;
import com.example.marketmailapp.mappers.CampaignMapper;
import com.example.marketmailapp.model.Campaign;
import com.example.marketmailapp.repository.CampaignRepository;
import com.example.marketmailapp.utils.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignServiceImpl implements CampaignService{
    private final CampaignRepository campaignRepository;

    private final CampaignMapper campaignMapper;

    private final EmailService emailService;


    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignMapper campaignMapper, EmailService emailService) {
        this.campaignRepository = campaignRepository;
        this.campaignMapper = campaignMapper;
        this.emailService = emailService;
    }


    public CampaignDTO createCampaign(CampaignDTO campaignDTO) {
        Campaign campaign = campaignMapper.campaignDTOToCampaign(campaignDTO);
        return campaignMapper.campaignToCampaignDTO(campaignRepository.save(campaign));
    }

    public List<CampaignDTO> getCampaignsByClientId(Long clientId) {
        return campaignRepository.findByClientId(clientId).stream()
                .map(campaignMapper :: campaignToCampaignDTO)
                .collect(Collectors.toList());
    }

    public void sendCampaignMail(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        CampaignDTO campaignDTO  = campaignMapper.campaignToCampaignDTO(campaign);

        campaignDTO.setStatus(MailStatus.DRAFT);
        EmailDTO emailDTO = new EmailDTO(campaignDTO.getClient_eMail(),
                                        campaignDTO.getSubscribers(),
                                        campaignDTO.getSubject(),
                                        campaignDTO.getEmailBody());

        emailService.sendEmail(emailDTO);
        // Mock sending email logic
        campaignDTO.setStatus(MailStatus.SENT);
        campaign = campaignMapper.campaignDTOToCampaign(campaignDTO);
        campaignRepository.save(campaign);
    }
}
