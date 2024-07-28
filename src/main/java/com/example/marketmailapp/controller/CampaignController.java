package com.example.marketmailapp.controller;

import com.example.marketmailapp.dto.CampaignDTO;
import com.example.marketmailapp.service.CampaignServiceImpl;
import com.example.marketmailapp.service.RequestCacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    private final CampaignServiceImpl campaignService;
    private final RequestCacheService requestCacheService;

    public CampaignController(CampaignServiceImpl campaignService, RequestCacheService requestCacheService) {
        this.campaignService = campaignService;
        this.requestCacheService = requestCacheService;
    }

    @PostMapping
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO campaignDTO) {
           return ResponseEntity.ok(campaignService.createCampaign(campaignDTO));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CampaignDTO>> getCampaignsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(campaignService.getCampaignsByClientId(clientId));
    }

    @PostMapping("/send/{campaignId}")
    public ResponseEntity<Void> sendCampaign(@PathVariable Long campaignId) {
        if(requestCacheService.isDuplicate(campaignId)){
            return ResponseEntity.badRequest().build();
        }
        requestCacheService.store(campaignId, "Already in Process");
        campaignService.sendCampaignMail(campaignId);
        requestCacheService.removeResponse(campaignId);
        return ResponseEntity.ok().build();
    }
}
