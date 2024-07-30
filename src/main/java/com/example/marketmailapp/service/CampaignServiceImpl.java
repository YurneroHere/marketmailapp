package com.example.marketmailapp.service;

import com.example.marketmailapp.dto.CampaignDTO;
import com.example.marketmailapp.dto.EmailDTO;
import com.example.marketmailapp.enums.MailStatus;
import com.example.marketmailapp.mappers.CampaignMapper;
import com.example.marketmailapp.model.Campaign;
import com.example.marketmailapp.repository.CampaignRepository;
import com.example.marketmailapp.utils.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void deleteById(Long id) {
        campaignRepository.deleteById(id);
    }


    public CampaignDTO getById(Long id) {
        Campaign campaign = campaignRepository.findById(id).orElse(null);
        return campaignMapper.campaignToCampaignDTO(campaign);
    }

    public CampaignDTO createCampaign(CampaignDTO campaignDTO) {
        if(emailService.validateEmailsIds(campaignDTO.getSubscribers(),
                campaignDTO.getClient_eMail())){
        Campaign campaign = campaignMapper.campaignDTOToCampaign(campaignDTO);
        return campaignMapper.campaignToCampaignDTO(campaignRepository.save(campaign));
        }
        throw new RuntimeException("invalid email ids");
    }



    public List<CampaignDTO> getCampaignsByClientId(Long clientId) {
        return campaignRepository.findByClientId(clientId).stream()
                .map(campaignMapper :: campaignToCampaignDTO)
                .collect(Collectors.toList());
    }

    public String sendCampaignMail(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        CampaignDTO campaignDTO  = campaignMapper.campaignToCampaignDTO(campaign);

        campaignDTO.setStatus(MailStatus.DRAFT);
        EmailDTO emailDTO = new EmailDTO(campaignDTO.getClient_eMail(),
                                        campaignDTO.getSubscribers(),
                                        campaignDTO.getSubject(),
                                        campaignDTO.getEmailBody());

        if(!emailService.sendEmail(emailDTO).equals(MailStatus.SENT.toString()))
            throw new RuntimeException("Email not sent");

        // Mock sending email logic
        campaignDTO.setStatus(MailStatus.SENT);
        campaign = campaignMapper.campaignDTOToCampaign(campaignDTO);
        campaignRepository.save(campaign);
        return "Email sent";
    }
}
